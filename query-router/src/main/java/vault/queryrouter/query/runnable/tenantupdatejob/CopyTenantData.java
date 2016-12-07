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

package vault.queryrouter.query.runnable.tenantupdatejob;

import vault.queryrouter.common.Constant;
import vault.queryrouter.common.util.SQLRegexUtil;
import vault.queryrouter.models.Query;
import vault.queryrouter.models.TenantUpdateJob;
import vault.queryrouter.models.dao.QueryDAO;

import java.io.File;

/**
 * Logic for executing copy data query
 */
public class CopyTenantData extends AbstractTenantUpdateJob {
  public CopyTenantData(TenantUpdateJob job) throws Exception {
    super(job);
  }

  public void run_() throws Exception {

    try {

      final String SAVE_DIR = Constant.UPLOAD_PATH;

      String local_path = SQLRegexUtil.getLocalFilePathFromCOPYStatement(rewroteSQL);

      String[] path_array = local_path.split("/");

      String fileName = path_array[path_array.length - 1];

      String newFileLocation = Constant.UPLOAD_PATH + pendingQuery.getConnectionId() + "_" +  fileName;

      Query q = QueryDAO.getQuery(session, pendingQuery.getQueryId());

      String sql = q.getQueryBody().replace(local_path, newFileLocation);

      stmt.execute(sql);

      //delete the file after copying the data to the MPPDB
      File saveFile = new File(SAVE_DIR + fileName);

      saveFile.delete();
    }
    catch(Exception e){
      logger.error(e.toString());
      throw e;
    }
  }
}
