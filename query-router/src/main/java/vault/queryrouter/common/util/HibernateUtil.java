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

package vault.queryrouter.common.util;

import vault.queryrouter.common.Constant;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util for Hibernate
 */
public class HibernateUtil {

  private static SessionFactory sessionFactory;
  private static Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

  private static void init() {

    //Standard hibernate XML
    StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder()
      .configure();

    //Configuration from config file
    registryBuilder.applySetting("hibernate.connection.url", Constant.getBackendDbConnString());
    registryBuilder.applySetting("hibernate.connection.username", Constant.getBackendDbUsername());
    registryBuilder.applySetting("hibernate.connection.password", Constant.getBackendDbPassword());

    StandardServiceRegistry registry = registryBuilder.build();

    try {
      sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    } catch (Throwable ex) {
      StandardServiceRegistryBuilder.destroy(registry);
      logger.error("Fail to init Hibernate",ex);
      throw new RuntimeException(ex);
    }
  }

  public static void shutdown() {
    sessionFactory.close();
    sessionFactory = null;
  }

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null)
      init();

    return sessionFactory;
  }
}