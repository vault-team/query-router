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


import vault.queryrouter.common.QueryLogStatus;
import vault.queryrouter.common.util.SQLRegexUtil;
import vault.queryrouter.models.Query;
import org.hibernate.Criteria;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

public class QueryDAO {
  public static Logger logger = LoggerFactory.getLogger(QueryDAO.class);

  public static String addQueryLog(StatelessSession session, String queryId, String queryBody, Timestamp startTime, Timestamp endTime, String commandType, int userId, int tenantMppdbId, String queryStatus, String mppdbId, String connectionId) {

    Query log = new Query(queryId, queryBody, startTime, endTime, commandType, userId, tenantMppdbId, queryStatus, mppdbId, connectionId);
    return (String) session.insert(log);
  }

  public static void updateQueryLog(StatelessSession session, String QuerylogID, Timestamp endTime, QueryLogStatus queryStatus) {
    Query query = (Query)session.get(Query.class, QuerylogID);
    query.setEndTime(endTime);
    query.setQueryStatus(queryStatus.toString());
    session.update(query);
  }

  public static void updateQueryLogWithError(StatelessSession session, String QuerylogID, Timestamp endTime, String errorMessage) {
    Query query = (Query)session.get(Query.class, QuerylogID);
    query.setEndTime(endTime);
    query.setQueryStatus(QueryLogStatus.Failure.toString());
    query.setErrorMessage(errorMessage);
    session.update(query);
  }

  public static Query getQuery(StatelessSession session, String queryId) throws Exception {
    Query query = null;
    try {
      Criteria criteria = session.createCriteria(Query.class);
      criteria.add(Expression.eq("queryId", queryId));
      query = (Query) criteria.uniqueResult();
    } catch(Exception e){
      logger.error("Failed to get Query",e);
      throw e;
    }
    return query;
  }

  /**
   * Find the last statement of `SET search_path `. If no, find the schema......
   * @param session
   * @param connectionId
   * @return
   * @throws Exception
   */
  public static String findSearchPath(StatelessSession session, String connectionId) throws Exception {
    try{

      String hql = "SELECT queryBody FROM Query WHERE SUBSTRING(LOWER(REPLACE(queryBody,' ','')),1,14)='setsearch_path' AND connectionId='"+connectionId+"' order by startTime asc";

      org.hibernate.Query query = session.createQuery(hql).setFirstResult(0);

      Transaction t = session.beginTransaction();
      List<String> queryLogs = query.list();
      t.commit();
      if(queryLogs.size()==0)
        return null;
      else {
        return SQLRegexUtil.getSchemaFromSetSearchPath(queryLogs.get(0));
      }

    } catch(Exception e){
      logger.error("Can't find search path",e);
      throw e;
    }
  }

  public static int getProcessingQuerySum(StatelessSession session, String mppdbId) {
    try {
      int count = 0;
      //Find out the query log record where end_time is null
      Criteria criteria = session.createCriteria(Query.class);
      criteria.add(Expression.eq("queryStatus", QueryLogStatus.Processing.toString()));
      criteria.add(Expression.eq("mppdbId", mppdbId));
      Iterator query = criteria.list().iterator();

      while (query.hasNext()) {
        query.next();
        count++;
      }
      return count;
    } catch(Exception e){
      logger.error("Can't get the sum of processing query",e);
      throw e;
    }
  }
}
