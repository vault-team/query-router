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

import vault.queryrouter.rewrite.impl.StaticRewriteFunction;

public class User {
  private int userId;
  private String userName;
  private String userRole;
  private String password;
  private int tenantMppdbId;

  public User() {
  }

  public User(String userName, String userRole, String password, int tenantMppdbId) {
    this.userName = userName;
    this.userRole = userRole;
    this.password = password;
    this.tenantMppdbId = tenantMppdbId;
  }

  public int getTenantMppdbId() {
    return tenantMppdbId;
  }

  public void setTenantMppdbId(int tenantMppdbId) {
    this.tenantMppdbId = tenantMppdbId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRewriteUsername(){
    return StaticRewriteFunction.rewriteUserName(this.tenantMppdbId, this.userName);
  }
}