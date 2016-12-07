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

import vault.queryrouter.models.TenantUpdateJob;
import vault.queryrouter.query.runnable.tenantupdatejob.CopyTenantData;
import vault.queryrouter.query.runnable.tenantupdatejob.ExecuteUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringTokenizer;

/**
 * A factory for creating TenantUpdateJob
 */
public class TenantUpdateJobFactory {

  private Logger logger = LoggerFactory.getLogger(getClass());

  public enum TenantUpdateJobType {
    copy, // for `COPY` SQL command
    execute; //for `UPDATE`, `INSERT`,`CREATE`..... SQL command

    public static TenantUpdateJobType getType(String s) {
      if (s.compareToIgnoreCase("copy") == 0)
        return copy;
      else
        return execute;
    }
  }

  public static TenantUpdateJobFactory instance;

  public static TenantUpdateJobFactory getInstance() {
    if (instance == null)
      instance = new TenantUpdateJobFactory();
    return instance;
  }


  public Runnable getRunnable(TenantUpdateJob job) throws Exception {
    StringTokenizer tokenizer = new StringTokenizer(job.getAction());
    String startsWith = tokenizer.nextToken();
    try {
      TenantUpdateJobType type = TenantUpdateJobType.getType(startsWith);
      switch (type) {
        case copy:
          return new CopyTenantData(job);
        case execute:
          return new ExecuteUpdate(job);
        default:
          throw new UnsupportedOperationException("Should not throw here...");
      }
    } catch (IllegalArgumentException e) {
      logger.error("Fail to execute : " + job.getAction(), e);
      throw e;
    }
  }
}
