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
import vault.queryrouter.models.ConnectionWrapper;
import vault.queryrouter.models.dao.QueryDAO;
import vault.queryrouter.rewrite.impl.RewriteContextInfo;
import vault.queryrouter.rewrite.impl.Rewriter;
import org.hibernate.StatelessSession;

import java.sql.Connection;
import java.sql.Statement;


public class VerticaUtil {

  public static void setSearchPath(ConnectionWrapper cw, StatelessSession session) throws Exception {
    int tenantMppdbId = cw.getTenantMPPDBId();
    String connectionId = cw.getConnectionId();
    Connection conn = cw.getConn();

    setSearchPath(connectionId, tenantMppdbId, conn, session);
  }

  public static void setSearchPath(String connection_id, int tenantMppdbId, Connection conn, StatelessSession session) throws Exception {

    String search_path = QueryDAO.findSearchPath(session, connection_id);
    // Search previous set search path query
    if(search_path==null)
      search_path = Constant.VAULT_TENANT_DEFAULT_SCHEMA;

    RewriteContextInfo info = new RewriteContextInfo();
    info.setTenantMppdbId(tenantMppdbId);
    String query = Rewriter.getInstance().rewrite(info, "SET SEARCH_PATH TO "+search_path);
    Statement stat = null;
    try{
      stat = conn.createStatement();
      stat.execute(query);
      conn.commit();
    }catch(Throwable e){
      throw e;
    }finally {
      stat.close();
    }
  }
}
