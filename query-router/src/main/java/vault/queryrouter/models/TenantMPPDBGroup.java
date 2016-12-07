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

public class TenantMPPDBGroup {
  private int tenantMppdbGroupId;
  private int groupSize;
  private Timestamp formationTime;
  private int nodeQuantity;

  public TenantMPPDBGroup() {
  }

  public TenantMPPDBGroup(int groupSize, Timestamp formationTime, int nodeQuantity) {
    this.groupSize = groupSize;
    this.formationTime = formationTime;
    this.nodeQuantity = nodeQuantity;
  }

  public int getTenantMppdbGroupId() {
    return tenantMppdbGroupId;
  }

  public void setTenantMppdbGroupId(int tenantMppdbGroupId) {
    this.tenantMppdbGroupId = tenantMppdbGroupId;
  }

  public int getGroupSize() {
    return groupSize;
  }

  public void setGroupSize(int groupSize) {
    this.groupSize = groupSize;
  }

  public Timestamp getFormationTime() {
    return formationTime;
  }

  public void setFormationTime(Timestamp formationTime) {
    this.formationTime = formationTime;
  }

  public int getNodeQuantity() {
    return nodeQuantity;
  }

  public void setNodeQuantity(int nodeQuantity) {
    this.nodeQuantity = nodeQuantity;
  }
}