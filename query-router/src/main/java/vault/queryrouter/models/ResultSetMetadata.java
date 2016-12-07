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


public class ResultSetMetadata {

  private String catalogName;
  private String columnClassName;
  private int columnCount;
  private int columnDisplaySize;
  private String columnLabel;
  private String columnName;
  private int columnType;
  private String columnTypeName;
  private int precision;
  private int scale;
  private String schemaName;
  private String tableName;
  private boolean autoIncrement;
  private boolean caseSensitive;
  private boolean currency;
  private boolean definitelyWritable;
  private int isNullable;
  private boolean readOnly;
  private boolean searchable;
  private boolean signed;
  private boolean writable;

  public String getCatalogName() {
    return catalogName;
  }

  public void setCatalogName(String catalogName) {
    this.catalogName = catalogName;
  }

  public String getColumnClassName() {
    return columnClassName;
  }

  public void setColumnClassName(String columnClassName) {
    this.columnClassName = columnClassName;
  }

  public int getColumnCount() {
    return columnCount;
  }

  public void setColumnCount(int columnCount) {
    this.columnCount = columnCount;
  }

  public int getColumnDisplaySize() {
    return columnDisplaySize;
  }

  public void setColumnDisplaySize(int columnDisplaySize) {
    this.columnDisplaySize = columnDisplaySize;
  }

  public String getColumnLabel() {
    return columnLabel;
  }

  public void setColumnLabel(String columnLabel) {
    this.columnLabel = columnLabel;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public int getColumnType() {
    return columnType;
  }

  public void setColumnType(int columnType) {
    this.columnType = columnType;
  }

  public String getColumnTypeName() {
    return columnTypeName;
  }

  public void setColumnTypeName(String columnTypeName) {
    this.columnTypeName = columnTypeName;
  }

  public int getPrecision() {
    return precision;
  }

  public void setPrecision(int precision) {
    this.precision = precision;
  }

  public int getScale() {
    return scale;
  }

  public void setScale(int scale) {
    this.scale = scale;
  }

  public String getSchemaName() {
    return schemaName;
  }

  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public boolean getAutoIncrement() {
    return autoIncrement;
  }

  public void setAutoIncrement(boolean autoIncrement) {
    this.autoIncrement = autoIncrement;
  }

  public boolean getCaseSensitive() {
    return caseSensitive;
  }

  public void setCaseSensitive(boolean caseSensitive) {
    this.caseSensitive = caseSensitive;
  }

  public boolean getCurrency() {
    return currency;
  }

  public void setCurrency(boolean currency) {
    this.currency = currency;
  }

  public boolean getDefinitelyWritable() {
    return definitelyWritable;
  }

  public void setDefinitelyWritable(boolean definitelyWritable) {
    this.definitelyWritable = definitelyWritable;
  }

  public int getIsNullable() {
    return isNullable;
  }

  public void setIsNullable(int isNullable) {
    this.isNullable = isNullable;
  }

  public boolean getReadOnly() {
    return readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  public boolean getSearchable() {
    return searchable;
  }

  public void setSearchable(boolean searchable) {
    this.searchable = searchable;
  }

  public boolean getSigned() {
    return signed;
  }

  public void setSigned(boolean signed) {
    this.signed = signed;
  }

  public boolean getWritable() {
    return writable;
  }

  public void setWritable(boolean writable) {
    this.writable = writable;
  }
}
