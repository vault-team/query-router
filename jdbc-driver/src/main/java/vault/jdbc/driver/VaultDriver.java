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

package vault.jdbc.driver;

import vault.jdbc.driver.io.CCConnection;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class VaultDriver implements Driver {

  final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VaultDriver.class);

  static {
    try {
      java.sql.DriverManager.registerDriver(new VaultDriver());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //From Main getConnection()
  public Connection connect(String url, Properties info) throws SQLException {
    if (!acceptsURL(url))
      throw new SQLException("URL is not accepted");

    try {

      logger.debug("Call to connect {} {}", url, info);

      String URL_PREFIX = "jdbc:vault://";

      String substring = url.trim().substring(URL_PREFIX.length(), url.length());

      String[] metadata = substring.split("/");

      String ip = metadata[0];
      String database = metadata[1];
      int tenantMppdbId = Integer.parseInt(metadata[2]);

      CCConnection conn = new CCConnection(ip, database, tenantMppdbId, info);
      VaultConnection vaultConnection = new VaultConnection(conn);
      vaultConnection.createConnection();
      return vaultConnection;
    }catch(Throwable e){
      throw new SQLException("Can't create connection",e);
    }

  }

  private static String URL_PREFIX = "jdbc:vault:";

  public boolean acceptsURL(String url) throws SQLException {
    return Pattern.matches(URL_PREFIX + ".*", url);
  }

  public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
    throws SQLException {
    logger.debug("Call to DriverPropertyInfo");
    DriverPropertyInfo[] props = new DriverPropertyInfo[]{};
    return props;
  }

  public int getMajorVersion() {
    logger.debug("Call to getMajorVersion");
    return 4;
  }

  public int getMinorVersion() {
    logger.debug("Call to getMinorVersion");
    return 0;
  }

  public boolean jdbcCompliant() {
    logger.debug("Call to jdbcCompliant");
    return true;
  }

  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new SQLFeatureNotSupportedException("Method not supported");
  }

}