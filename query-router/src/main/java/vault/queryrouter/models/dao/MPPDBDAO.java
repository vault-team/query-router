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


import vault.queryrouter.common.util.HibernateUtil;
import vault.queryrouter.models.MPPDB;
import org.hibernate.Criteria;
import org.hibernate.StatelessSession;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class MPPDBDAO {


  private static SessionFactory factory = HibernateUtil.getSessionFactory();
  public static Logger logger = LoggerFactory.getLogger(MPPDBDAO.class);

  public static List<MPPDB> getMppdbs(StatelessSession session, int tenantMppdbGroupId) {

    //Find out the mmpdb_id which belongs to the current tenant group
    Criteria criteria = session.createCriteria(MPPDB.class);
    criteria.add(Expression.eq("tenantMppdbGroupId", tenantMppdbGroupId));
    return (List<MPPDB>)criteria.list();
  }

  public static String getMppdbId(StatelessSession session, String mppdbIp) {

    String mppdbId = null;

    Criteria criteria = session.createCriteria(MPPDB.class);
    criteria.add(Expression.eq("mppdbIp", mppdbIp));
    Iterator mppdbs = criteria.list().iterator();

    while (mppdbs.hasNext()) {
      MPPDB mppdb = (MPPDB) mppdbs.next();
      mppdbId = mppdb.getMppdbId();
    }

    return mppdbId;
  }
}
