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

import vault.jdbc.driver.io.CCStatement;
import vault.jdbc.driver.io.HttpClient;
import vault.query.result.SerializableResultSet;

import java.sql.*;

public class VaultStatement implements Statement {

  private VaultConnection vaultConnection;
  private CCStatement statement;
  HttpClient client = new HttpClient();

  public VaultStatement(VaultConnection vaultConnection, CCStatement stat) {
    this.vaultConnection = vaultConnection;
    this.statement = stat;
  }

  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new SQLException("Method not supported: unwrap");
  }

  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    throw new SQLException("Method not supported: isWrapperFor");
    //return iface == java.sql.Statement.class || iface == VaultStatement.class;
  }

  public ResultSet executeQuery(String sql) throws SQLException {

    try {
      SerializableResultSet resultset = client.statement_executeQuery(vaultConnection.connection, sql);

      return new VaultResultSet(resultset);

    } catch (Exception e) {
      throw new SQLException(e.toString(), "Cannot process the executeQuery", e);
    }
  }

  public int executeUpdate(String sql) throws SQLException {

    try {
      client.statement_executeUpdate(vaultConnection.connection, sql);

    } catch (Exception e) {
      throw new SQLException(e.toString(), "Cannot process the executeUpdate", e);
    }
    return -1;
  }

  public boolean execute(String sql) throws SQLException {
    try {
      boolean returnValue = client.statement_execute(vaultConnection.connection, sql);
      //true if ResultSet
      return returnValue;
    } catch (Exception e){
      throw new SQLException(e.toString(),e);
    }

  }

  public void close() throws SQLException {

  }

  public int getMaxFieldSize() throws SQLException {
    throw new SQLException("Method not supported: getMaxFieldSize");
  }

  public void setMaxFieldSize(int max) throws SQLException {
    throw new SQLException("Method not supported: setMaxFieldSize");
  }

  public int getMaxRows() throws SQLException {
        /*
        Client client = null;
        try {
            client = this.connection.lockClient();
            return client.statement_getMaxRows(statement);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.connection.unlockClient(client);
        }*/
    return -1;
  }

  public void setMaxRows(int max) throws SQLException {
        /*Client client = null;
        try {
            client = this.connection.lockClient();
            client.statement_setMaxRows(statement, max);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.connection.unlockClient(client);
        }*/
  }

  public void setEscapeProcessing(boolean enable) throws SQLException {
    throw new SQLException("Method not supported: setEscapeProcessing");
  }

  public int getQueryTimeout() throws SQLException {
        /*Client client = null;
        try {
            client = this.connection.lockClient();
            return client.statement_getQueryTimeout(statement);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.connection.unlockClient(client);
        }*/
    return -1;
  }

  public void setQueryTimeout(int seconds) throws SQLException {
        /*Client client = null;
        try {
            client = this.connection.lockClient();
            client.statement_setQueryTimeout(statement, seconds);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.connection.unlockClient(client);
        }*/
  }

  public void cancel() throws SQLException {
        /*Client client = null;
        try {
            client = this.connection.lockClient();
            client.statement_cancel(statement);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.connection.unlockClient(client);
        }*/
  }

  public SQLWarning getWarnings() throws SQLException {
        /*Client client = null;
        try {
            client = this.connection.lockClient();
            statement_getWarnings_return warn = client.statement_getWarnings(statement);
            return vault.jdbc.driver.VaultWarning.buildFromList(warn.warnings);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.connection.unlockClient(client);
        }*/
    return null;
  }

  public void clearWarnings() throws SQLException {
        /*Client client = null;
        try {
            client = this.connection.lockClient();
            client.statement_clearWarnings(statement);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.connection.unlockClient(client);
        }*/
  }

  public void setCursorName(String name) throws SQLException {
    throw new SQLException("Method not supported: setCursorName");
  }

  public ResultSet getResultSet() throws SQLException {
    SerializableResultSet resultset = client.statement_getResultSet();
    return new VaultResultSet(resultset);
  }

  public int getUpdateCount() throws SQLException {
        /*Client client = null;
        try {
            client = this.connection.lockClient();
            return client.statement_getUpdateCount(statement);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.connection.unlockClient(client);
        }*/
    return -1;
  }

  public boolean getMoreResults() throws SQLException {
    return false;
  }

  public void setFetchDirection(int direction) throws SQLException {
    throw new SQLException("Method not supported: setFetchDirection");
  }

  public int getFetchDirection() throws SQLException {
    throw new SQLException("Method not supported: getFetchDirection");
  }

  public void setFetchSize(int rows) throws SQLException {
    throw new SQLException("Method not supported: setFetchSize");
  }

  public int getFetchSize() throws SQLException {
    throw new SQLException("Method not supported: getFetchSize");
  }

  public int getResultSetConcurrency() throws SQLException {
    throw new SQLException("Method not supported: getResultSetConcurrency");
  }

  public int getResultSetType() throws SQLException {
     /*   Client client = null;
        try {
            client = this.connection.lockClient();
            return client.statement_getResultSetType(statement);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.connection.unlockClient(client);
        }*/
    return -1;
  }

  public void addBatch(String sql) throws SQLException {
    throw new SQLException("Method not supported: addBatch");
  }

  public void clearBatch() throws SQLException {
    throw new SQLException("Method not supported: clearBatch");
  }

  public int[] executeBatch() throws SQLException {
    throw new SQLException("Method not supported: executeBatch");
  }

  public Connection getConnection() throws SQLException {
    return this.vaultConnection;
  }

  public boolean getMoreResults(int current) throws SQLException {
    throw new SQLException("Method not supported: getMoreResults");
  }

  public ResultSet getGeneratedKeys() throws SQLException {
    throw new SQLException("Method not supported: getGeneratedKeys");
  }

  public int executeUpdate(String sql, int autoGeneratedKeys)
    throws SQLException {
    throw new SQLException("Method not supported:executeUpdate");
  }

  public int executeUpdate(String sql, int[] columnIndexes)
    throws SQLException {
    throw new SQLException("Method not supported: executeUpdate");
  }

  public int executeUpdate(String sql, String[] columnNames)
    throws SQLException {
    throw new SQLException("Method not supported: executeUpdate");
  }

  public boolean execute(String sql, int autoGeneratedKeys)
    throws SQLException {
    throw new SQLException("Method not supported: execute");
  }

  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    throw new SQLException("Method not supported: execute");
  }

  public boolean execute(String sql, String[] columnNames)
    throws SQLException {
    throw new SQLException("Method not supported: execute");
  }

  public int getResultSetHoldability() throws SQLException {
    throw new SQLException("Method not supported: getResultSetHoldability");
  }

  public boolean isClosed() throws SQLException {
    throw new SQLException("Method not supported: isClosed");
  }

  public void setPoolable(boolean poolable) throws SQLException {
    throw new SQLException("Method not supported: setPoolable");
  }

  public boolean isPoolable() throws SQLException {
    throw new SQLException("Method not supported: isPoolable");
  }

  public void closeOnCompletion() throws SQLException {
    throw new SQLException("Method not supported: closeOnCompletion");
  }

  public boolean isCloseOnCompletion() throws SQLException {
    throw new SQLException("Method not supported: isCloseOnCompletion");
  }

}