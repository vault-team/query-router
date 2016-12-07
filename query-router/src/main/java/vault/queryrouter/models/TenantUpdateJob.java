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

import vault.queryrouter.common.TenantUpdateJobType;

import java.sql.Timestamp;

public class TenantUpdateJob {
  private Integer jobQueueId;
  private String action;
  private String status;
  private String queryId;
  private String connectionId;
  private String workerId;
  private Timestamp lastTouchTime;
  private String type;
  private int tenantMppdbId;

  public TenantUpdateJob(){

  }

  public TenantUpdateJob(String action, String status, String queryId, String connectionId, TenantUpdateJobType type, int tenantMppdbId) {
    this.action = action;
    this.status = status;
    this.queryId = queryId;
    this.connectionId = connectionId;
    this.type = type.toString();
    this.tenantMppdbId = tenantMppdbId;
  }

  public Integer getJobQueueId() {
    return this.jobQueueId;
  }

  public void setJobQueueId(Integer jobQueueId) {
    this.jobQueueId = jobQueueId;
  }

  public String getAction() {
    return this.action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getQueryId() {
    return this.queryId;
  }

  public void setQueryId(String queryId) {
    this.queryId = queryId;
  }

  public String getConnectionId() {
    return this.connectionId;
  }

  public void setConnectionId(String connectionId) {
    this.connectionId = connectionId;
  }

  public String getWorkerId() {
    return this.workerId;
  }

  public void setWorkerId(String workerId) {
    this.workerId = workerId;
  }

  public Timestamp getLastTouchTime() {
    return this.lastTouchTime;
  }

  public void setLastTouchTime(Timestamp lastTouchTime) {
    this.lastTouchTime = lastTouchTime;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getTenantMppdbId() { return tenantMppdbId; }

  public void setTenantMppdbId(int tenantMppdbId) {this.tenantMppdbId = tenantMppdbId; }
}
