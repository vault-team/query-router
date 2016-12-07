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

package vault.queryrouter.models.dao;

import vault.queryrouter.common.TenantUpdateJobStatus;
import vault.queryrouter.common.TenantUpdateJobType;
import vault.queryrouter.models.Query;
import vault.queryrouter.models.TenantUpdateJob;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class JobQueueDAO {

  public static Logger logger = LoggerFactory.getLogger(JobQueueDAO.class);

  public static Integer addQueryToJobQueue(StatelessSession session, String sql, TenantUpdateJobStatus status, String queryId, String connectionId, int tenantMppdbId) {
    TenantUpdateJob log = new TenantUpdateJob(sql, status.toString(), queryId, connectionId, TenantUpdateJobType.queryrouter, tenantMppdbId);
    return (Integer) session.insert(log);
  }

  public static int getProcessingQuery(StatelessSession session, int tenantMppdbId) {
    int counter = 0;

    Criteria criteria = session.createCriteria(TenantUpdateJob.class);
    criteria.add(Expression.eq("status", TenantUpdateJobStatus.Processing.toString()));
    criteria.add(Expression.or(Expression.eq("action", "tenant_mppdb_data_movement"), Expression.eq("action", "mppdb_copy")));
    criteria.add(Expression.eq("tenantMppdbId", tenantMppdbId));
    Iterator updateJobs = criteria.list().iterator();

    if (updateJobs != null) {
      while (updateJobs.hasNext()) {
        updateJobs.next();
        counter++;
      }
    }

    return counter;
  }

  public static void deleteFromJobQueue(StatelessSession session, int logID) {
    TenantUpdateJob query = (TenantUpdateJob)session.get(TenantUpdateJob.class, logID);
    session.delete(query);
  }

  public static TenantUpdateJob getPendingQueryLock(StatelessSession session, String workerId) throws Exception {

    try {
      session.getTransaction().begin();
      //Update the pending query(order by last_touch_timestamp) status from pending to processing
      String hql = "UPDATE TenantUpdateJob set status= '" + TenantUpdateJobStatus.Processing.toString() + "', workerId = '" + workerId + "' WHERE status='" + TenantUpdateJobStatus.Pending.toString() + "' AND type ='"+TenantUpdateJobType.queryrouter.toString()+"' ORDER BY lastTouchTime LIMIT 1";
      org.hibernate.Query query = session.createQuery(hql);
      int result = query.executeUpdate();
      session.getTransaction().commit();

      //Pending query exists and the status changes from "Pending" to "Processing"
      if (result != 0) {

        //Find the processing query by the thread(workerId)
        Criteria criteria = session.createCriteria(TenantUpdateJob.class);
        criteria.add(Expression.eq("workerId", workerId.toString()));
        criteria.add(Expression.eq("status", TenantUpdateJobStatus.Processing.toString()));
        TenantUpdateJob pendingQueueCandidate = (TenantUpdateJob) criteria.uniqueResult();

        Query q = QueryDAO.getQuery(session, pendingQueueCandidate.getQueryId());

        //Check the availability of the processing Query that could be actual processed
        //If the number of  conflictQuery is more than 0 which means the processing query conflicts with the another processing query(e.g.move_tenant_mppdb_data)
        int conflictQuery = JobQueueDAO.getProcessingQuery(session, q.getTenantMppdbId());

        //No conflict query -> Return the query(TenantUpdateJob Object)
        if (conflictQuery == 0) {

          return pendingQueueCandidate;
        }
        //Conflict query exists -> Status of the processing query turn back to "Pending" and the last_touch_timestamp will be updated
        else {

          try {
            session.getTransaction().begin();
            TenantUpdateJob jq = (TenantUpdateJob) session.get(TenantUpdateJob.class, pendingQueueCandidate.getJobQueueId());
            jq.setStatus(TenantUpdateJobStatus.Pending.toString());
            jq.setWorkerId("");
            session.update(jq);
            session.getTransaction().commit();

          } catch (HibernateException e) {
            logger.error("Exception: ", e);
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
          }
          return null;
        }
      }
      //No pending query exists
      else {
        return null;
      }

    } catch (Exception e) {
      logger.error(e.toString());
      throw e;
    }
  }

}
