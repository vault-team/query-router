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
import vault.queryrouter.models.User;
import vault.queryrouter.models.TenantUpdateJob;
import vault.queryrouter.models.dao.UserDAO;

import java.sql.SQLException;
import java.util.Iterator;

/**
 * Logic for executing copy data query
 */
public class ExecuteUpdate extends AbstractTenantUpdateJob {

  public ExecuteUpdate(TenantUpdateJob job) throws Exception {
    super(job);
  }

  public void grantPermissionToAllUsers(int tenantMppdbId, String db_name, String schemaName) throws SQLException {
    Iterator users = UserDAO.getUserList(session, tenantMppdbId);
    try {
      while (users.hasNext()) {
        User user = (User) users.next();
        String grantStatement = "GRANT ALL ON SCHEMA " + db_name + "." + schemaName + " TO " + "tm" + tenantMppdbId + "_" + user.getUserName();
        stmt.execute(grantStatement);
      }

    } catch(Exception e){
      logger.error("Fail to grant permission for users",e);
      throw e;
    }
  }

  public void run_() throws Exception {
    try {
      stmt.execute(rewroteSQL);

      if(SQLRegexUtil.isCreateSchemaStatement(rewroteSQL)){
        grantPermissionToAllUsers(pendingQuery.getTenantMppdbId(), Constant.VERTICA_DEFAULT_DATABASE, SQLRegexUtil.getSchemaFromCreateSchemaStatement(rewroteSQL));
      }
    } catch(Exception e){
      logger.error("Fail to query",e);
      throw e;
    }
  }
}
