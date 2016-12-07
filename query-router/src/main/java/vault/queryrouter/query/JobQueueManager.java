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

import vault.queryrouter.common.Constant;
import vault.queryrouter.common.util.HibernateUtil;
import vault.queryrouter.models.TenantUpdateJob;
import vault.queryrouter.models.dao.JobQueueDAO;
import vault.queryrouter.query.runnable.TenantUpdateJobFactory;
import org.hibernate.StatelessSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Manager For Tenants' update and data migration
 */
public class JobQueueManager {

  public static JobQueueManager singleton = null;
  private Logger logger = LoggerFactory.getLogger(getClass());
  ThreadPoolExecutor updateExecutor = new ThreadPoolExecutor(0, Constant.UPDATE_THREAD_POOL_SIZE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
  ExecutorService daemonExecutor = Executors.newSingleThreadExecutor();
  DaemonCheckJobThread daemon = null;

  /**
   * Init a daemon thread to repeatedly check the job queue and distributed
   */
  public JobQueueManager() {
    daemon = new DaemonCheckJobThread();
    daemonExecutor.submit(daemon);
  }

  public static JobQueueManager getInstance() {
    if (singleton == null) {
      singleton = new JobQueueManager();
    }
    return singleton;
  }

  public void close() {
    daemon.close();
    daemonExecutor.shutdown();
    updateExecutor.shutdown();
    try {
      if (!updateExecutor.awaitTermination(24, TimeUnit.HOURS)) {
        logger.error("Something wrong with the updateJob. Some updateJob don't finish ");
        daemonExecutor.shutdownNow();
      }
    } catch (Throwable e) {
      logger.error("Fail to turn down the executor", e);
    }
  }

  private class DaemonCheckJobThread implements Runnable {

    public Logger logger = LoggerFactory.getLogger(getClass());

    public boolean close = false;

    public void close() {
      close = true;
    }

    /**
     * Simple implementation: try to get job and submit to thread pool
     */
    public void run() {
      while (!close) {
        if(updateExecutor.getMaximumPoolSize() - updateExecutor.getActiveCount() > 0) {
          StatelessSession session = null;
          try {
            session = HibernateUtil.getSessionFactory().openStatelessSession();
            TenantUpdateJob job = JobQueueDAO.getPendingQueryLock(session, Constant.WORKER_ID);
            if(job != null)
              updateExecutor.submit(TenantUpdateJobFactory.getInstance().getRunnable(job));
          } catch (Throwable e) {
            logger.error("Error in the daemon thread", e);
          } finally {
            session.close();
          }
        }

        //sleep here
        try {
          TimeUnit.SECONDS.sleep(Constant.UPDATEJOB_CHECK_TIME_SECOND);
        } catch (InterruptedException e) {
          logger.error("Error in the daemon thread", e);
        }
      }
    }
  }
}
