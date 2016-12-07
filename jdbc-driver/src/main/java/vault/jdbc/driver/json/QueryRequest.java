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

package vault.jdbc.driver.json;


public class QueryRequest {

  private String sql;
  private String queryId;
  private String fileName;
  private String connectionId;
  private int userId;
  private int tenantMppdbId;
  private String mppdbId;
  private String commandType;

  public QueryRequest() {
  }

  public QueryRequest(String sql, String mppdbId, String connectionId, int userId, int tenantMppdbId, String commandType) {
    this.sql = sql;
    this.mppdbId = mppdbId;
    this.connectionId = connectionId;
    this.userId = userId;
    this.tenantMppdbId = tenantMppdbId;
    this.commandType = commandType;

  }

  public String getSql() {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public String getMppdbId() {
    return mppdbId;
  }

  public void setMppdbId(String mppdbId) {
    this.mppdbId = mppdbId;
  }

  public String getQueryId() {
    return queryId;
  }

  public void setQueryId(String queryId) {
    this.queryId = queryId;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getConnectionId() {
    return connectionId;
  }

  public void setConnectionId(String connectionId) {
    this.connectionId = connectionId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getTenantMppdbId() {
    return tenantMppdbId;
  }

  public void setTenantMppdbId(int tenantMppdbId) {
    this.tenantMppdbId = tenantMppdbId;
  }

  public String getCommandType() {
    return commandType;
  }

  public void setCommandType(String commandType) {
    this.commandType = commandType;
  }
}