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

import vault.query.result.SerializableResultSetMetaData;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class VaultResultSetMetaData implements ResultSetMetaData {

  private SerializableResultSetMetaData metadata;

  public VaultResultSetMetaData(SerializableResultSetMetaData metadata) {
    this.metadata = metadata;
  }


  public boolean isWrapperFor(Class<?> arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public <T> T unwrap(Class<T> arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public String getCatalogName(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).catalogName;
  }


  public String getColumnClassName(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).columnClassName;
  }


  public int getColumnCount() throws SQLException {
    return this.metadata.parts.size();
  }


  public int getColumnDisplaySize(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).columnDisplaySize;
  }


  public String getColumnLabel(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).columnLabel;
  }


  public String getColumnName(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).columnName;
  }


  public int getColumnType(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).columnType;
  }


  public String getColumnTypeName(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).columnTypeName;
  }


  public int getPrecision(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).precision;
  }


  public int getScale(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).scale;
  }


  public String getSchemaName(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).schemaName;
  }


  public String getTableName(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).tableName;
  }


  public boolean isAutoIncrement(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).autoIncrement;
  }


  public boolean isCaseSensitive(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).caseSensitive;
  }


  public boolean isCurrency(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).currency;
  }


  public boolean isDefinitelyWritable(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).definitelyWritable;
  }


  public int isNullable(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).nullable;
  }


  public boolean isReadOnly(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).readOnly;
  }


  public boolean isSearchable(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).searchable;
  }


  public boolean isSigned(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).signed;
  }


  public boolean isWritable(int arg0) throws SQLException {
    return this.metadata.parts.get(arg0 - 1).writable;
  }

  public int findColumn(String columnLabel) {
    // Check columnLabel
    for (int i = 0; i < this.metadata.parts.size(); i++) {
      if (columnLabel.equals(this.metadata.parts.get(i).columnLabel)) {
        return i + 1;
      }
    }
    // Check columnName
    for (int i = 0; i < this.metadata.parts.size(); i++) {
      if (columnLabel.equals(this.metadata.parts.get(i).columnName)) {
        return i + 1;
      }
    }
    return -1;
  }

}