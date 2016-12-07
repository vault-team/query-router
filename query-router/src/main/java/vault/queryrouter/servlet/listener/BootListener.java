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

package vault.queryrouter.servlet.listener;

import vault.queryrouter.common.Constant;
import vault.queryrouter.common.util.HibernateUtil;
import vault.queryrouter.query.JobQueueManager;
import vault.queryrouter.query.QueryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * Bootup actions
 */
public class BootListener implements ServletContextListener {

  private Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * Init QueryManager and JobQueueManager
   *
   * @param servletContextEvent
   */
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    logger.info("initiate the system");

    //Check whether the temp dir exist, if not, create the dir
    checkAndCreateDir(Constant.DOWNLOAD_PATH);
    checkAndCreateDir(Constant.UPLOAD_PATH);

    QueryManager.getInstance();
    JobQueueManager.getInstance();
  }

  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    logger.info("shutdown the system");
    QueryManager.getInstance().close();
    JobQueueManager.getInstance().close();
    HibernateUtil.shutdown();
  }

  public void checkAndCreateDir(String path){
    File dir = new File(path);
    if(!dir.exists()){
      dir.mkdirs();
    }
  }
}
