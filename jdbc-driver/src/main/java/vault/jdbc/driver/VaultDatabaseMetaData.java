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

public class VaultDatabaseMetaData {
  /*implements DatabaseMetaData {


    final Logger logger = LoggerFactory.getLogger(VaultDatabaseMetaData.class);

    private VaultConnection vaultConnection;
    private CCConnection connection;

    public VaultDatabaseMetaData(VaultConnection connection) {
        this.vaultConnection = connection;
        this.connection = connection.connection;

    }


    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public <T> T unwrap(Class<T> arg0) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean allProceduresAreCallable() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean allTablesAreSelectable() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean deletesAreDetected(int arg0) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean generatedKeyAlwaysReturned() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getAttributes(String arg0, String arg1, String arg2,
            String arg3) throws SQLException {
        throw new SQLException("Method not supported");w
    }


    public ResultSet getBestRowIdentifier(String arg0, String arg1,
            String arg2, int arg3, boolean arg4) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public String getCatalogSeparator() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getCatalogSeparator(connection);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return "";
    }


    public String getCatalogTerm() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getCatalogTerm(connection);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return "";
    }


    public ResultSet getCatalogs() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            ResultSet resultset = client.connection_getCatalogs(connection);
            return new VaultResultSet(resultset);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return null;
    }


    public ResultSet getClientInfoProperties() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getColumnPrivileges(String arg0, String arg1, String arg2,
            String arg3) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern,
            String columnNamePattern) throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            ResultSet resulset = client.connection_getColumns(connection, catalog, schemaPattern, tableNamePattern, columnNamePattern);
            return new VaultResultSet(resulset);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return null;
    }


    public Connection getConnection() throws SQLException {
        return this.VaultConnection;
    }


    public ResultSet getCrossReference(String arg0, String arg1, String arg2,
            String arg3, String arg4, String arg5) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public int getDatabaseMajorVersion() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getstaticmetadata(connection).getDatabaseMajorVersion();
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return -1;
    }


    public int getDatabaseMinorVersion() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getstaticmetadata(connection).getDatabaseMinorVersion();
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return -1;
    }


    public String getDatabaseProductName() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getstaticmetadata(connection).getDatabaseProductName();
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return "";
    }


    public String getDatabaseProductVersion() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getstaticmetadata(connection).getDatabaseProductVersion();
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return "";
    }


    public int getDefaultTransactionIsolation() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getstaticmetadata(connection).getDefaultTransactionIsolation();
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return -1;
    }


    public int getDriverMajorVersion() {
        return 4;
    }


    public int getDriverMinorVersion() {
        return 0;
    }


    public String getDriverName() throws SQLException {
        return "Crystal";
    }


    public String getDriverVersion() throws SQLException {
        return "0.1";
    }


    public ResultSet getExportedKeys(String arg0, String arg1, String arg2)
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public String getExtraNameCharacters() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getFunctionColumns(String arg0, String arg1, String arg2,
            String arg3) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getFunctions(String arg0, String arg1, String arg2)
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public String getIdentifierQuoteString() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getstaticmetadata(connection).getIdentifierQuoteString();
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return "";
    }


    public ResultSet getImportedKeys(String arg0, String arg1, String arg2)
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getIndexInfo(String arg0, String arg1, String arg2,
            boolean arg3, boolean arg4) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public int getJDBCMajorVersion() throws SQLException {
        // TODO Auto-generated method stub
        return 4;
    }


    public int getJDBCMinorVersion() throws SQLException {
        // TODO Auto-generated method stub
        return 1;
    }


    public int getMaxBinaryLiteralLength() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxCatalogNameLength() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxCharLiteralLength() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxColumnNameLength() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxColumnsInGroupBy() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxColumnsInIndex() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxColumnsInOrderBy() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxColumnsInSelect() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxColumnsInTable() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxConnections() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxCursorNameLength() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxIndexLength() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxProcedureNameLength() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxRowSize() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxSchemaNameLength() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxStatementLength() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxStatements() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxTableNameLength() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxTablesInSelect() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getMaxUserNameLength() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public String getNumericFunctions() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getPrimaryKeys(String arg0, String arg1, String arg2)
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getProcedureColumns(String arg0, String arg1, String arg2,
            String arg3) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public String getProcedureTerm() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getProcedures(String arg0, String arg1, String arg2)
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getPseudoColumns(String arg0, String arg1, String arg2,
            String arg3) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public int getResultSetHoldability() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public RowIdLifetime getRowIdLifetime() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public String getSQLKeywords() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getSQLKeywords(connection);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return "";
    }


    public int getSQLStateType() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }


    public String getSchemaTerm() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getSchemaTerm(connection);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return "";
    }


    public ResultSet getSchemas() throws SQLException {
        return getSchemas(null, null);
    }


    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            ResultSet resultset = client.connection_getSchemas(connection, catalog, schemaPattern);
            return new VaultResultSet(resultset);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        

        transportLock.lock();
        try {
          if (stmtHandle != null) {
            TCancelOperationReq cancelReq = new TCancelOperationReq(stmtHandle);
            TCancelOperationResp cancelResp = client.CancelOperation(cancelReq);
            Utils.verifySuccessWithInfo(cancelResp.getStatus());
          }
        } catch (SQLException e) {
          throw e;
        } catch (Exception e) {
          throw new SQLException(e.toString(), "08S01", e);
        } finally {
          transportLock.unlock();
        }
        return null;
    }


    public String getSearchStringEscape() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public String getStringFunctions() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getSuperTables(String arg0, String arg1, String arg2)
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getSuperTypes(String arg0, String arg1, String arg2)
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public String getSystemFunctions() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getTablePrivileges(String arg0, String arg1, String arg2)
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getTableTypes() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            ResultSet resultset = client.connection_getTableTypes(connection);
            return new VaultResultSet(resultset);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return null;
    }


    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern,
            String[] types) throws SQLException 
    {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            ResultSet resultset = client.connection_getTables(connection, catalog, schemaPattern, tableNamePattern, tabToList(types));
            return new VaultResultSet(resultset);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return null;
    }
    
    private static List<String> tabToList(String[] types) {
        List<String> typesTab = null;
            if (types!=null) {
                typesTab = Arrays.asList(types);
            }
        return typesTab;
    }
    

    public String getTimeDateFunctions() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getTypeInfo() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            ResultSet resultset = client.connection_getTypeInfo(connection);
            return new VaultResultSet(resultset);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return null;
    }


    public ResultSet getUDTs(String arg0, String arg1, String arg2, int[] arg3)
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public String getURL() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public String getUserName() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public ResultSet getVersionColumns(String catalog, String schema, String table)
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean insertsAreDetected(int arg0) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean isCatalogAtStart() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean isReadOnly() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getReadOnly(connection);
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return false;
    }


    public boolean locatorsUpdateCopy() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean nullPlusNonNullIsNull() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean nullsAreSortedAtEnd() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean nullsAreSortedAtStart() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean nullsAreSortedHigh() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean nullsAreSortedLow() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean othersDeletesAreVisible(int arg0) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean othersInsertsAreVisible(int arg0) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean othersUpdatesAreVisible(int arg0) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean ownDeletesAreVisible(int arg0) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean ownInsertsAreVisible(int arg0) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean ownUpdatesAreVisible(int arg0) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean storesLowerCaseIdentifiers() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean storesMixedCaseIdentifiers() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean storesUpperCaseIdentifiers() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsANSI92FullSQL() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsBatchUpdates() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getstaticmetadata(connection).isSupportsCatalogsInTableDefinitions();
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return false;
    }


    public boolean supportsColumnAliasing() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsConvert() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsConvert(int arg0, int arg1) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsCoreSQLGrammar() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsCorrelatedSubqueries() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsDataDefinitionAndDataManipulationTransactions()
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsDataManipulationTransactionsOnly()
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsExpressionsInOrderBy() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsExtendedSQLGrammar() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsFullOuterJoins() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsGetGeneratedKeys() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsGroupBy() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsGroupByBeyondSelect() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsGroupByUnrelated() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsLikeEscapeClause() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsLimitedOuterJoins() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsMinimumSQLGrammar() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsMultipleOpenResults() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsMultipleResultSets() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsMultipleTransactions() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsNamedParameters() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsNonNullableColumns() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsOrderByUnrelated() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsOuterJoins() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsPositionedDelete() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsPositionedUpdate() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsResultSetConcurrency(int arg0, int arg1)
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsResultSetHoldability(int arg0) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsResultSetType(int arg0) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsSavepoints() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getstaticmetadata(connection).isSupportsSavepoints();
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return false;
    }


    public boolean supportsSchemasInDataManipulation() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getstaticmetadata(connection).isSupportsSchemasInDataManipulation();
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return false;
    }


    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        Client client = null;
        try {
            client = this.VaultConnection.lockClient();
            return client.connection_getstaticmetadata(connection).isSupportsSchemasInTableDefinitions();
        } catch (CCSQLException e) {
            throw new SQLException(e.reason, e.sqlState, e.vendorCode, e);
        } catch (Exception e) {
            throw new SQLException(e.toString(), "08S01", e);
        } finally {
            this.VaultConnection.unlockClient(client);
        }
        return false;
    }


    public boolean supportsSelectForUpdate() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsStatementPooling() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsStoredProcedures() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsSubqueriesInComparisons() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsSubqueriesInExists() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsSubqueriesInIns() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsTableCorrelationNames() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsTransactionIsolationLevel(int arg0)
            throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsTransactions() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsUnion() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean supportsUnionAll() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean updatesAreDetected(int arg0) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean usesLocalFilePerTable() throws SQLException {
        throw new SQLException("Method not supported");
    }


    public boolean usesLocalFiles() throws SQLException {
        throw new SQLException("Method not supported");
    }
*/
}