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

public class MPPDB {
  private String mppdbId;
  private String mppdbIp;
  private int tenantMppdbGroupId;


  public MPPDB() {
  }

  public MPPDB(String mppdbIp, int tenantMppdbGroupId) {
    this.mppdbIp = mppdbIp;
    this.tenantMppdbGroupId = tenantMppdbGroupId;
  }

  public String getMppdbId() {
    return mppdbId;
  }

  public void setMppdbId(String mppdbId) {
    this.mppdbId = mppdbId;
  }

  public String getMppdbIp() {
    return mppdbIp;
  }

  public void setMppdbIp(String mppdbIp) {
    this.mppdbIp = mppdbIp;
  }

  public int getTenantMppdbGroupId() {
    return tenantMppdbGroupId;
  }

  public void setTenantMppdbGroupId(int tenantMppdbGroupId) {
    this.tenantMppdbGroupId = tenantMppdbGroupId;
  }


}