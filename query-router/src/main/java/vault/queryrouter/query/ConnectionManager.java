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

package vault.queryrouter.query;


import vault.common.models.ConnectionRequest;
import vault.queryrouter.common.Constant;
import vault.queryrouter.common.exception.NoMPPDBException;
import vault.queryrouter.models.ConnectionWrapper;
import vault.queryrouter.models.MPPDB;
import vault.queryrouter.models.User;
import vault.queryrouter.models.dao.QueryDAO;
import vault.queryrouter.models.dao.TenantMPPDBDAO;
import vault.queryrouter.models.dao.UserDAO;
import org.hibernate.StatelessSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {

  public Logger logger = LoggerFactory.getLogger(getClass());
  ConcurrentHashMap<String, ConnectionWrapper> connectionMap = new ConcurrentHashMap<String, ConnectionWrapper>();
  public static ConnectionManager singleton = null;

  public int getNumByMPPDBId(StatelessSession session, String mppdbId){
    int count = 0;

    count = QueryDAO.getProcessingQuerySum(session, mppdbId);

    return count;

  }

  public static ConnectionManager getInstance() {
    if (singleton == null) {
      singleton = new ConnectionManager();
    }
    return singleton;
  }

  public boolean equalTenantMppdbGroupId(String connectionId, int tenantMppdbGroupId){
    if(connectionMap.get(connectionId).getTenantMPPDBGroupId() == tenantMppdbGroupId){
      return true;
    }
    else
      return false;
  }

  public Connection DriverManagerGetConnection(String url, String userName, String password) throws Exception{
    Connection conn = DriverManager.getConnection(url, userName, password);
    conn.setAutoCommit(false);
    return conn;
  }

  public ConnectionWrapper getConnectionWrapper(String connectionId){
    return connectionMap.get(connectionId);
  }

  public ConnectionWrapper changeConnectionAfterTenantGroupChange(StatelessSession session, String connectionId) throws Exception {
    try {

      //Clear old connection
      ConnectionWrapper oldConn = connectionMap.get(connectionId);

      oldConn.getConn().close();
      connectionMap.remove(connectionId);

      //Create new Connection
      return this.createConnection(session, oldConn.getUser());

    } catch(Exception e){
      logger.error("Cannot make a new Connection",e);
      throw e;
    }
  }

  public Connection createMPPDBConnection(MPPDB mpppdb, User user) throws Exception {
    logger.info(String.format("DB instance connected mppdb ip: %s", mpppdb.getMppdbIp()));
    Class.forName(Constant.DRIVER);
    String url = Constant.URL_HEADER + mpppdb.getMppdbIp() + ":" + Constant.URL_PORT + "/" + Constant.VERTICA_DEFAULT_DATABASE;
    return DriverManagerGetConnection(url, user.getRewriteUsername(), user.getPassword());
  }

  /**
   * Create Connection by using User object
   * @param session
   * @param user
   * @return
   * @throws NoMPPDBException
   */
  public ConnectionWrapper createConnection(StatelessSession session, User user) throws NoMPPDBException {
    MPPDB mppdb = null;
    List<String> excluded_mppdb_list = new ArrayList<String>();
    Connection conn = null;
    String mppdbIp = "";

    MPPDBFinder finder = MPPDBFinder.getInstance();

    boolean connected = false;
    while(connected == false) {
      try {
        mppdb = finder.getAvblMppdb(session, user.getTenantMppdbId(), excluded_mppdb_list);
        mppdbIp = mppdb.getMppdbIp();
        conn = this.createMPPDBConnection(mppdb, user);
        connected = true;
      } catch (NoMPPDBException e){
        logger.error("All MPPDB tried give up....");
        throw e;
      } catch (Exception e) {
        logger.info("User " + user.getUserName()+ " cannot be found in MPPDB with " + mppdbIp);
        excluded_mppdb_list.add(mppdbIp);
      }
    }

    String connectionId = UUID.randomUUID().toString();

    logger.debug(String.format("Generated ConnectionID: %s ",connectionId));

    int tenantMppdbGroupId = TenantMPPDBDAO.getTenantMppdbGroupId(session, user.getTenantMppdbId());
    ConnectionWrapper wrapper = new ConnectionWrapper(connectionId, conn, user, mppdb, tenantMppdbGroupId);
    connectionMap.put(connectionId, wrapper);

    return wrapper;
  }

  /**
   * Create MPPDB connection when user request through HTTP API (JDBC)
   *
   * @param connectionRequest
   * @return String connection_id
   * @throws Exception
   */
  public String createConnection(StatelessSession session, ConnectionRequest connectionRequest) throws Exception{

    int tenantMppdbId = connectionRequest.getTenantMppdbId();

    // Check user name and password also
    User user = UserDAO.getUser(session, tenantMppdbId, connectionRequest.getUserName(), connectionRequest.getUserPassword());
    ConnectionWrapper conn = this.createConnection(session, user);

    connectionRequest.setUserId(user.getUserId());
    connectionRequest.setMppdbId(conn.getMppdbId());
    connectionRequest.setConnectionId(conn.getConnectionId());

    return conn.getConnectionId();
  }

  public void closeConnection(String connectionid) throws SQLException {
    ConnectionWrapper cw = connectionMap.get(connectionid);

    connectionMap.get(connectionid).getConn().close();
    connectionMap.remove(connectionid);

    cw.getConn().close();
    String mppdbId = cw.getMppdbId();

    logger.info("Connection Close. Mppdb_id: " + mppdbId);

  }
}
