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

package vault.queryrouter.query.runnable;

import vault.queryrouter.common.QueryLogStatus;
import vault.queryrouter.common.util.HibernateUtil;
import vault.queryrouter.common.util.VerticaUtil;
import vault.queryrouter.models.ConnectionWrapper;
import vault.queryrouter.models.dao.QueryDAO;
import vault.queryrouter.models.dao.TenantMPPDBDAO;
import vault.queryrouter.query.ConnectionManager;
import vault.queryrouter.query.TenantQueryResultFileGenerator;
import vault.queryrouter.rewrite.impl.RewriteContextInfo;
import vault.queryrouter.rewrite.impl.Rewriter;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;
import java.util.concurrent.Callable;

public class SelectQueryExecution implements Callable<Void> {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private int userId;
  private int tenantMppdbId;
  private UUID queryId;
  private String mppdbId;
  private Connection conn;
  private String sql;
  private String fileName;
  private String commandType;
  private RewriteContextInfo context = new RewriteContextInfo();
  private String QuerylogID = "";
  private String connectionId;
  private StatelessSession session;
  private ConnectionWrapper connectionWrapper;
  private boolean getSelectResult = false;


  public SelectQueryExecution(String sql, String mppdbId, int userId, int tenantMppdbId, UUID queryId, String fileName, String commandType, String QuerylogID, String connectionId) {
    this.sql = sql;
    this.userId = userId;
    this.tenantMppdbId = tenantMppdbId;
    this.queryId = queryId;
    this.mppdbId = mppdbId;
    this.fileName = fileName;
    this.commandType = commandType;
    this.context.setTenantMppdbId(tenantMppdbId);
    this.QuerylogID = QuerylogID;
    this.connectionId = connectionId;
    this.connectionWrapper = ConnectionManager.getInstance().getConnectionWrapper(connectionId);
    this.session = HibernateUtil.getSessionFactory().openStatelessSession();

  }

  public Void call() throws Exception {

    Statement stmt = null;
    java.sql.Timestamp endTimestamp;
    ResultSet result = null;
    Rewriter writer = Rewriter.getInstance();
    Connection conn = null;

    try {

      //Rewrite the username in sql
      String rewriteSql = writer.rewrite(context, sql);

      //Find the result of SELECT statement until obtaining the result
      while(getSelectResult == false) {
        try {
          //Get connection
          conn = connectionWrapper.getConn();
          VerticaUtil.setSearchPath(connectionWrapper, session);

          stmt = conn.createStatement();
          logger.info("Query send with the connection String "+conn.getMetaData().getURL());

          //Use execute() whatever execute or executeQuery
          if (commandType.equals("execute") || commandType.equals("executeQuery")) {
            result = stmt.executeQuery(rewriteSql);
          }
          getSelectResult = true;
        } catch(Exception e){

          int tenantMppdbGroupId = TenantMPPDBDAO.getTenantMppdbGroupId(session, this.tenantMppdbId);

          //Tenant MPPDB Group is changed, connection need to be renewed
          if (!ConnectionManager.getInstance().equalTenantMppdbGroupId(this.connectionId, tenantMppdbGroupId)){
              this.connectionWrapper = ConnectionManager.getInstance().changeConnectionAfterTenantGroupChange(session, this.connectionWrapper.getConnectionId());
          }
          //Tenant MPPDB Group is not changed, but the connection is failed
          else{
            throw e;
          }

        }
      }

      endTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
      Transaction tx = session.beginTransaction();
      QueryDAO.updateQueryLog(session, QuerylogID, endTimestamp, QueryLogStatus.Success);
      TenantQueryResultFileGenerator.generateCsvFile(result, queryId.toString());

      conn.commit();
      tx.commit();


    } catch (Exception e) {
      try{
        conn.rollback();
      } catch(Throwable er){
        logger.error("Can't rollback",er);
      }

      endTimestamp = new java.sql.Timestamp(System.currentTimeMillis());

      Transaction tx = session.beginTransaction();
      QueryDAO.updateQueryLogWithError(session, QuerylogID, endTimestamp, e.getMessage());
      tx.commit();

      logger.error("Failed to handle user query", e);
      throw e;
    } finally {
      try {
        session.close();
        if (stmt != null)
          stmt.close();
        //conn.close();
      } catch (Exception e) {
        logger.error("Failed to close and clean user query", e);
        throw e;
      }
    }
    return null;
  }
}