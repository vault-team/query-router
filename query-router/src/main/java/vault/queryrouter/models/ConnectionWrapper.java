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

package vault.queryrouter.models;

import java.sql.Connection;

public class ConnectionWrapper {

  private User user;
  private MPPDB mppdb;
  private int tenantMPPDBGroupId;
  private String connectionId;
  private Connection conn;

  public ConnectionWrapper(String connectionId, Connection conn, User user, MPPDB mppdb, int tenantMPPDBGroupId){
    this.conn = conn;
    this.user = user;
    this.connectionId = connectionId;
    this.mppdb = mppdb;
    this.tenantMPPDBGroupId = tenantMPPDBGroupId;
  }

  public Connection getConn(){
    return this.conn;
  }

  public int getTenantMPPDBGroupId(){
    return this.tenantMPPDBGroupId;
  }

  public String getConnectionId() {
    return connectionId;
  }

  public int getTenantMPPDBId() {
    return this.user.getTenantMppdbId();
  }

  public int getUserId() {
    return this.user.getUserId();
  }

  public String getMppdbId() {
    return this.mppdb.getMppdbId();
  }

  public User getUser(){
    return this.user;
  }
}
