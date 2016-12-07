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

import java.sql.Timestamp;

public class Query {

  private String queryId;
  private Timestamp startTime;
  private Timestamp endTime;
  private String commandType;
  private String queryBody;
  private int tenantMppdbId;
  private String queryStatus;
  private String mppdbId;
  private int userId;
  private String connectionId;
  private String errorMessage = "";

  public Query() {
  }

  public Query(String queryId, String queryBody, Timestamp startTime, Timestamp endTime, String commandType, int userId, int tenantMppdbId, String queryStatus, String mppdbId, String connectionId) {
    this.queryId = queryId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.commandType = commandType;
    this.queryBody = queryBody;
    this.userId = userId;
    this.tenantMppdbId = tenantMppdbId;
    this.queryStatus = queryStatus;
    this.mppdbId = mppdbId;
    this.connectionId = connectionId;
  }

  public String getQueryId() {
    return queryId;
  }

  public void setQueryId(String queryId) {
    this.queryId = queryId;
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }

  public String getCommandType() {
    return commandType;
  }

  public void setCommandType(String commandType) {
    this.commandType = commandType;
  }

  public String getQueryBody() {
    return queryBody;
  }

  public void setQueryBody(String queryBody) {
    this.queryBody = queryBody;
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

  public String getQueryStatus() {
    return queryStatus;
  }

  public void setQueryStatus(String queryStatus) {
    this.queryStatus = queryStatus;
  }

  public String getMppdbId() {
    return mppdbId;
  }

  public void setMppdbId(String mppdbId) {
    this.mppdbId = mppdbId;
  }

  public String getConnectionId(){ return connectionId; }

  public void setConnectionId(String connectionId){ this.connectionId = connectionId; }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}