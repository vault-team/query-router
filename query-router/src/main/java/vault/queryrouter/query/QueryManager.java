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

package vault.queryrouter.query;


import vault.common.util.VerticaSQLRegexUtil;
import vault.queryrouter.common.Constant;
import vault.queryrouter.common.QueryLogStatus;
import vault.queryrouter.common.TenantUpdateJobStatus;
import vault.queryrouter.models.Query;
import vault.queryrouter.models.dao.JobQueueDAO;
import vault.queryrouter.models.dao.QueryDAO;
import vault.queryrouter.query.runnable.SelectQueryExecution;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.*;

public class QueryManager {

  public Logger logger = LoggerFactory.getLogger(getClass());
  public static QueryManager singleton = null;
  ExecutorService selectExecutor = Executors.newFixedThreadPool(Constant.SELECT_THREAD_POOL_SIZE);

  public static QueryManager getInstance() {
    if (singleton == null) {
      singleton = new QueryManager();
    }
    return singleton;
  }

  public UUID processQuery(StatelessSession session, String sql, String mppdbId, int userId, int tenantMppdbId, String fileName, String commandType, String connectionId) throws Exception {

    UUID queryId = UUID.randomUUID();

    //Add the record to the Query Log
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      //startTimestamp will be null, but it will have current timestamp in backend database
      //endTimestamp will be null in backend-database
      String QuerylogID = QueryDAO.addQueryLog(session, queryId.toString(), sql, null, null, commandType, userId, tenantMppdbId, QueryLogStatus.Processing.toString(), mppdbId, connectionId);

      StringTokenizer tokenizer = new StringTokenizer(sql);
      String startsWith = tokenizer.nextToken();

      //Deal with the SELECT statement
      if (startsWith.equalsIgnoreCase("SELECT")) {
        Callable<Void> callableTask = new SelectQueryExecution(sql, mppdbId, userId, tenantMppdbId, queryId, fileName, commandType, QuerylogID, connectionId);
        selectExecutor.submit(callableTask);
      }
      //Deal with the UPDATE statement
      else {
        //Append the Update statement to the Job Queue Table, not execute the Update statement
        JobQueueDAO.addQueryToJobQueue(session, sql, TenantUpdateJobStatus.Pending, queryId.toString(), connectionId, tenantMppdbId);
      }
      tx.commit();
      return queryId;
    } catch (Exception e) {
      logger.error("Fail to process query", e);
      tx.rollback();
      throw e;
    }

  }


  public String checkFinishExecution(StatelessSession session, String queryId) throws Exception {
    Query q = QueryDAO.getQuery(session, queryId);
    String status = q.getQueryStatus();
    switch (QueryLogStatus.valueOf(status)) {
      case Failure:
        throw VerticaSQLRegexUtil.getSQLExceptionFromMessage(q.getErrorMessage());
      default:
        return q.getQueryStatus();
    }

  }

  public String getResult(String queryId) throws InterruptedException, ExecutionException {

    String fileName = queryId + ".csv";

    return fileName;

  }

  public void close() {
    selectExecutor.shutdown();
    try {
      if (!selectExecutor.awaitTermination(24, TimeUnit.HOURS)) {
        logger.error("Something wrong with the updateJob. Some updateJob don't finish ");
        selectExecutor.shutdownNow();
      }
    } catch (Throwable e) {
      logger.error("Fail to turn down the executor", e);
    }
  }
}