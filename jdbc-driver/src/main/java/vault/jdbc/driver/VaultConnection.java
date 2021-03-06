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
import vault.jdbc.driver.io.CCStatement;
import vault.jdbc.driver.io.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class VaultConnection implements Connection {

  final Logger logger = LoggerFactory.getLogger(VaultConnection.class);

  CCConnection connection;
  HttpClient client = new HttpClient();
  boolean isClosed;

  public VaultConnection(CCConnection conn) {

    this.connection = conn;
    this.isClosed = false;
  }


  public VaultConnection createConnection() throws Exception {
    try {
      client.create_connection(connection);
    } catch (Exception e) {
      throw e;
    }
    return this;
  }


  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new SQLException("Method not supported");
  }

  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    throw new SQLException("Method not supported");
  }

  public Statement createStatement() throws SQLException {
    return createStatement(ResultSet.TYPE_FORWARD_ONLY,
      ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
  }

  public PreparedStatement prepareStatement(String sql) throws SQLException {
    throw new SQLException("Method not supported");
  }

  public CallableStatement prepareCall(String sql) throws SQLException {
    throw new SQLException("Method not supported");
  }

  public String nativeSQL(String sql) throws SQLException {
    return sql;
  }

  public void setAutoCommit(boolean autoCommit) throws SQLException {
  /*Client client = null;
  try {
	  client = this.lockClient();
	  client.connection_setAutoCommit(connection, autoCommit);
	} catch (CCSQLException e) {
	  throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
	} catch (Exception e) {
	  throw new SQLException(e.toString(), "08S01", e);
	} finally {
	  this.unlockClient(client);
	}*/
  }

  public boolean getAutoCommit() throws SQLException {
  /*Client client = null;
  try {
	  client = this.lockClient();
	  return client.connection_getAutoCommit(connection);
	} catch (CCSQLException e) {
	  throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
	} catch (Exception e) {
	  throw new SQLException(e.toString(), "08S01", e);
	} finally {
	  this.unlockClient(client);
	}*/
    return false;
  }

  public void commit() throws SQLException {
    throw new SQLException("Method not supported");
  }

  public void rollback() throws SQLException {
    throw new SQLException("Method not supported");
  }

  public void close() throws SQLException {
    if (!this.isClosed) {
      try {
        internalClose();
      } finally {
        this.isClosed = true;
      }
    }
  }

  public void internalClose() throws SQLException {
    try {
      client.close_connection(connection);
    } catch (Exception e) {
      throw new SQLException(e.toString());
    }
  }

  public boolean isClosed() throws SQLException {
    return this.isClosed;
  }

  public DatabaseMetaData getMetaData() throws SQLException {

    return null;
    //return new VaultDatabaseMetaData(this);
  }

  public void setReadOnly(boolean readOnly) throws SQLException {
	/*Client client = null;
	try {
	  client = this.lockClient();
	  client.connection_setReadOnly(connection, readOnly);
	} catch (CCSQLException e) {
	  throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
	} catch (Exception e) {
	  throw new SQLException(e.toString(), "08S01", e);
	} finally {
	  this.unlockClient(client);
	}*/
  }

  public boolean isReadOnly() throws SQLException {
	/*Client client = null;
	try {
	  client = this.lockClient();
	  return client.connection_getReadOnly(connection);
	} catch (CCSQLException e) {
	  throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
	} catch (Exception e) {
	  throw new SQLException(e.toString(), "08S01", e);
	} finally {
	  this.unlockClient(client);
	}*/
    return false;
  }

  public void setCatalog(String catalog) throws SQLException {
	/*Client client = null;
	try {
	  client = this.lockClient();
	  client.connection_setCatalog(connection, catalog);
	} catch (CCSQLException e) {
	  throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
	} catch (Exception e) {
	  throw new SQLException(e.toString(), "08S01", e);
	} finally {
	  this.unlockClient(client);
	}*/
  }

  public String getCatalog() throws SQLException {
	/*Client client = null;
	try {
	  client = this.lockClient();
	  return client.connection_getCatalog(connection);
	} catch (CCSQLException e) {
	  throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
	} catch (Exception e) {
	  throw new SQLException(e.toString(), "08S01", e);
	} finally {
	  this.unlockClient(client);
	}*/
    return "";
  }

  public void setTransactionIsolation(int level) throws SQLException {
	/*Client client = null;
	try {
	  client = this.lockClient();
	  client.connection_setTransactionIsolation(connection, level);
	} catch (CCSQLException e) {
	  throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
	} catch (Exception e) {
	  throw new SQLException(e.toString(), "08S01", e);
	} finally {
	  this.unlockClient(client);
	}*/
  }

  public int getTransactionIsolation() throws SQLException {
	/*Client client = null;
	try {
	  client = this.lockClient();
	  return client.connection_getTransactionIsolation(connection);
	} catch (CCSQLException e) {
	  throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
	} catch (Exception e) {
	  throw new SQLException(e.toString(), "08S01", e);
	} finally {
	  this.unlockClient(client);
	}*/
    return -1;
  }

  public SQLWarning getWarnings() throws SQLException {
    return null;
  }

  public void clearWarnings() throws SQLException {
  }

  public Statement createStatement(int resultSetType, int resultSetConcurrency)
    throws SQLException {
    return createStatement(resultSetType, resultSetConcurrency,
      ResultSet.CLOSE_CURSORS_AT_COMMIT);
  }

  public PreparedStatement prepareStatement(String sql, int resultSetType,
                                            int resultSetConcurrency) throws SQLException {
    throw new SQLException("Method not supported");
  }

  public CallableStatement prepareCall(String sql, int resultSetType,
                                       int resultSetConcurrency) throws SQLException {
    throw new SQLException("Method not supported");
  }

  public Map<String, Class<?>> getTypeMap() throws SQLException {
    throw new SQLException("Method not supported");
  }

  public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    throw new SQLException("Method not supported");
  }

  public void setHoldability(int holdability) throws SQLException {
    throw new SQLException("Method not supported");
  }

  public int getHoldability() throws SQLException {
    throw new SQLException("Method not supported");
  }

  public Savepoint setSavepoint() throws SQLException {
    throw new SQLException("Method not supported");
  }

  public Savepoint setSavepoint(String name) throws SQLException {
    throw new SQLException("Method not supported");
  }

  public void rollback(Savepoint savepoint) throws SQLException {
    throw new SQLException("Method not supported");
  }

  public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    throw new SQLException("Method not supported");
  }

  public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {

    try {
      CCStatement statement = new CCStatement();
      return new VaultStatement(this, statement);
    } catch (Exception e) {
      throw new SQLException(e.toString(), "08S01", e);
    }

  }

  /*
	public Statement createStatement(String sql, String database, int tenantid){
	  CCStatement statement = new CCStatement(sql,database,tenantid);
	  return new VaultStatement(this,statement);
	}
  */
  public PreparedStatement prepareStatement(String sql, int resultSetType,
                                            int resultSetConcurrency, int resultSetHoldability)
    throws SQLException {
    throw new SQLException("Method not supported: prepareStatement");
  }

  public CallableStatement prepareCall(String sql, int resultSetType,
                                       int resultSetConcurrency, int resultSetHoldability)
    throws SQLException {
    throw new SQLException("Method not supported");
  }

  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
    throws SQLException {
    throw new SQLException("Method not supported");
  }

  public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
    throws SQLException {
    throw new SQLException("Method not supported");
  }

  public PreparedStatement prepareStatement(String sql, String[] columnNames)
    throws SQLException {
    throw new SQLException("Method not supported");
  }

  public Clob createClob() throws SQLException {
    throw new SQLException("Method not supported");
  }

  public Blob createBlob() throws SQLException {
    throw new SQLException("Method not supported");
  }

  public NClob createNClob() throws SQLException {
    throw new SQLException("Method not supported");
  }

  public SQLXML createSQLXML() throws SQLException {
    throw new SQLException("Method not supported");
  }

  public boolean isValid(int timeout) throws SQLException {
    try {
      return internalIsValid(timeout);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private boolean internalIsValid(int timeout) throws SQLException {
	/*
	Client client = null;
	try {
	  client = this.lockClient();
	  return client.connection_isvalid(connection, timeout);
	} catch (CCSQLException e) {
	  throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
	} catch (Exception e) {
	  throw new SQLException(e.toString(), "08S01", e);
	} finally {
	  this.unlockClient(client);
	}*/
    return false;
  }

  public void setClientInfo(String name, String value)
    throws SQLClientInfoException {
    throw new SQLClientInfoException();
  }

  public void setClientInfo(Properties properties)
    throws SQLClientInfoException {
    throw new SQLClientInfoException();
  }

  public String getClientInfo(String name) throws SQLException {
    throw new SQLException("Method not supported");
  }

  public Properties getClientInfo() throws SQLException {
    throw new SQLException("Method not supported");
  }

  public Array createArrayOf(String typeName, Object[] elements)
    throws SQLException {
    throw new SQLException("Method not supported");
  }

  public Struct createStruct(String typeName, Object[] attributes)
    throws SQLException {
    throw new SQLException("Method not supported");
  }

  public void setSchema(String schema) throws SQLException {
	/*Client client = null;
	try {
	  client = this.lockClient();
	  client.connection_setSchema(connection, schema);
	} catch (CCSQLException e) {
	  throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
	} catch (Exception e) {
	  throw new SQLException(e.toString(), "08S01", e);
	} finally {
	  this.unlockClient(client);
	}*/
  }

  public String getSchema() throws SQLException {
	/*Client client = null;
	try {
	  client = this.lockClient();
	  return client.connection_getSchema(connection);
	} catch (CCSQLException e) {
	  throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
	} catch (Exception e) {
	  throw new SQLException(e.toString(), "08S01", e);
	} finally {
	  this.unlockClient(client);
	}*/
    return "";
  }

  public void abort(Executor executor) throws SQLException {
    throw new SQLException("Method not supported");
  }

  public void setNetworkTimeout(Executor executor, int milliseconds)
    throws SQLException {
    throw new SQLException("Method not supported");
  }

  public int getNetworkTimeout() throws SQLException {
    throw new SQLException("Method not supported");
  }

}