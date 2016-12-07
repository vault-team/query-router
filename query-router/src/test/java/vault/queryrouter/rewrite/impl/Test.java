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

package vault.queryrouter.rewrite.impl;

import vault.queryrouter.common.util.HibernateUtil;
import vault.queryrouter.models.TenantUpdateJob;
import vault.queryrouter.models.dao.JobQueueDAO;
import org.hibernate.StatelessSession;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by Julian on 5/7/2016.
 */
public class Test {
  private static SessionFactory factory = HibernateUtil.getSessionFactory();
  private static Logger logger = LoggerFactory.getLogger(Test.class);

  public static void main(String[] args) {
    StatelessSession session = factory.openStatelessSession();
    UUID uuid = UUID.randomUUID();
    try {
      while (true) {
        TenantUpdateJob pendingQuery = JobQueueDAO.getPendingQueryLock(session, uuid.toString());
//        Thread.sleep(5000);
      }
    } catch (Exception e) {
      session.close();
      logger.info("Exception:", e);
    } finally {

    }
  }
}

