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

package vault.jdbc.driver.io;

import java.util.Properties;

public class CCConnection {


  String connectionId;
  String ip;
  String database;
  Properties info;
  String mppdbId;
  int userId;
  int tenantMppdbId;

  public CCConnection() {
  }

  public CCConnection(String ip, String database, int tenantMppdbId, Properties info) {
    this.ip = ip;
    this.database = database;
    this.tenantMppdbId = tenantMppdbId;
    this.info = info;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getIp() {
    return ip;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public String getDatabase() {
    return database;
  }

  public void setInfo(Properties info) {
    this.info = info;
  }

  public Properties getInfo() {
    return info;
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

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getUserId() {
    return userId;
  }

  public void setTenantMppdbId(int tenantMppdbId) {
    this.tenantMppdbId = tenantMppdbId;
  }

  public int getTenantMppdbId() {
    return tenantMppdbId;
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CCConnection)
      return this.equals((CCConnection) that);
    return false;
  }

  public boolean equals(CCConnection that) {
    if (that == null)
      return false;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.connectionId != that.connectionId)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("CCConnection(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.connectionId);
    first = false;
    sb.append(")");
    return sb.toString();
  }


}