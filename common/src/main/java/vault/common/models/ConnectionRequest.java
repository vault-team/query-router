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

package vault.common.models;

public class ConnectionRequest {

  private String userName;
  private String userPassword;
  private String database;
  private String mppdbId;
  private String connectionId;
  private int tenantMppdbId;
  private int userId;

  public ConnectionRequest() {
  }

  public ConnectionRequest(String database, int tenantMppdbId, String userName, String userPassword) {
    this.database = database;
    this.tenantMppdbId = tenantMppdbId;
    this.userName = userName;
    this.userPassword = userPassword;
  }

  public ConnectionRequest(String connectionId) {
    this.connectionId = connectionId;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public String getDatabase() {
    return database;
  }

  public void setMppdbId(String mppdbId) {
    this.mppdbId = mppdbId;
  }

  public String getMppdbId() {
    return mppdbId;
  }

  public void setConnectionId(String connectionId) {
    this.connectionId = connectionId;
  }

  public String getConnectionId() {
    return connectionId;
  }

  public void setTenantMppdbId(int tenantMppdbId) {
    this.tenantMppdbId = tenantMppdbId;
  }

  public int getTenantMppdbId() {
    return tenantMppdbId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getUserId() {
    return userId;
  }

}
