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

public class TenantMPPDB {
  private int tenantMppdbId;
  private int tenantMppdbGroupId;
  private int requestNodeQuantity;
  private String flavor;
  private String tenantId;
  private String tenantMppdbName;

  public TenantMPPDB() {
  }

  public TenantMPPDB(int tenantMppdbGroupId, int requestNodeQuantity, String flavor, String tenantMppdbName) {
    this.tenantMppdbGroupId = tenantMppdbGroupId;
    this.flavor = flavor;
    this.tenantMppdbName = tenantMppdbName;
    this.requestNodeQuantity = requestNodeQuantity;
  }

  public int getTenantMppdbId() {
    return tenantMppdbId;
  }

  public void setTenantMppdbId(int tenantMppdbId) {
    this.tenantMppdbId = tenantMppdbId;
  }

  public int getTenantMppdbGroupId() {
    return tenantMppdbGroupId;
  }

  public void setTenantMppdbGroupId(int tenantMppdbGroupId) {
    this.tenantMppdbGroupId = tenantMppdbGroupId;
  }

  public int getRequestNodeQuantity() {
    return requestNodeQuantity;
  }

  public void setRequestNodeQuantity(int requestNodeQuantity) {
    this.requestNodeQuantity = requestNodeQuantity;
  }

  public String getFlavor() {
    return flavor;
  }

  public void setFlavor(String flavor) {
    this.flavor = flavor;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getTenantMppdbName() {
    return tenantMppdbName;
  }

  public void setTenantMppdbName(String tenantMppdbName) {
    this.tenantMppdbName = tenantMppdbName;
  }
}