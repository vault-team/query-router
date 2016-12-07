/*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package vault.queryrouter.query.runnable.tenantupdatejob;

import vault.queryrouter.common.QueryLogStatus;
import vault.queryrouter.common.util.HibernateUtil;
import vault.queryrouter.common.util.VerticaUtil;
import vault.queryrouter.models.ConnectionWrapper;
import vault.queryrouter.models.MPPDB;
import vault.queryrouter.models.TenantUpdateJob;
import vault.queryrouter.models.dao.JobQueueDAO;
import vault.queryrouter.models.dao.MPPDBDAO;
import vault.queryrouter.models.dao.QueryDAO;
import vault.queryrouter.models.dao.TenantMPPDBDAO;
import vault.queryrouter.query.ConnectionManager;
import vault.queryrouter.rewrite.impl.RewriteContextInfo;
import vault.queryrouter.rewrite.impl.Rewriter;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * Abstract class for tenant update job.
 *
 * Update query should be executed in every MPPDB
 * stmt, conn contain Connection and Statement of each MPPDB
 * Subclasses override the run_() and define the logic to execute query with stmt and conn.
 */
public abstract class AbstractTenantUpdateJob implements Runnable {

  protected Logger logger = LoggerFactory.getLogger(getClass());
  protected TenantUpdateJob pendingQuery;
  protected StatelessSession session;

  protected Statement stmt;
  private Connection conn;
  private ConnectionWrapper cw;
  protected String rewroteSQL;

  public AbstractTenantUpdateJob(TenantUpdateJob job) throws Exception {
    this.pendingQuery = job;
    this.session = HibernateUtil.getSessionFactory().openStatelessSession();

    //Rewrite sql
    Rewriter writer = Rewriter.getInstance();
    RewriteContextInfo context;
    context = new RewriteContextInfo();
    context.setTenantMppdbId(pendingQuery.getTenantMppdbId());
    rewroteSQL = writer.rewrite(context, pendingQuery.getAction());

    cw = ConnectionManager.getInstance().getConnectionWrapper(pendingQuery.getConnectionId());
  }

  public void run() {
    Transaction tx = null;
    try {
      int tenantMppdbGroupId = TenantMPPDBDAO.getTenantMppdbGroupId(session, pendingQuery.getTenantMppdbId());

      //update all corresponding mppdb
      for(MPPDB mppdb: MPPDBDAO.getMppdbs(session, tenantMppdbGroupId)){
        try {
          //Instead of reusing connection in ConnectionManager, we open new connection for each MPPDBs
          conn = ConnectionManager.getInstance().createMPPDBConnection(mppdb, cw.getUser());
          conn.setAutoCommit(false);

          // set search path
	        VerticaUtil.setSearchPath(cw.getConnectionId(),cw.getTenantMPPDBId(),conn, session);
          stmt = conn.createStatement();
          run_();
          conn.commit();
        }
        catch(Exception e){
          logger.info("MPPDB with " + mppdb.getMppdbIp() + " cannot not be updated", e);
          try{
            conn.rollback();
          }catch(Throwable rollback_e){
            logger.error("Fail to rollback exception");
          }
          throw e;

        }finally{
          if(stmt!= null) stmt.close();
          if(conn!= null) conn.close();
          stmt = null;
          conn = null;
        }
      }
      java.sql.Timestamp endTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
      tx = session.beginTransaction();
      QueryDAO.updateQueryLog(session, pendingQuery.getQueryId(), endTimestamp, QueryLogStatus.Success);
      JobQueueDAO.deleteFromJobQueue(session, pendingQuery.getJobQueueId());
      tx.commit();

    } catch (Exception e) {
	    //Handling Exception
	    //TODO: if there are 3 MPPDBs and 2 success and 1 fail, how to rollback the previous MPPDB ?
	    logger.error("Fail to execute the job", e);

	    try {
		    if(tx != null) tx.rollback();
	    } catch (Throwable e1) {
		    logger.error("Fail to rollback", e1);
	    }

	    //Begin new transaction to update the logging table
	    tx = session.beginTransaction();
	    Timestamp endTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
      if(e instanceof SQLException)
	      QueryDAO.updateQueryLogWithError(session, pendingQuery.getQueryId(), endTimestamp, e.getMessage());
      else
        QueryDAO.updateQueryLog(session, pendingQuery.getQueryId(), endTimestamp, QueryLogStatus.Failure);
      JobQueueDAO.deleteFromJobQueue(session, pendingQuery.getJobQueueId());
	    tx.commit();
    } finally {
	    session.close();
    }
  }

  public abstract void run_() throws Exception;

}