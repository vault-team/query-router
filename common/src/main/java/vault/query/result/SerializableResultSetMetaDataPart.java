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

package vault.query.result;

public class SerializableResultSetMetaDataPart {

  public String catalogName; 
  public String columnClassName; 
  public int columnDisplaySize; 
  public String columnLabel; 
  public String columnName; 
  public int columnType; 
  public String columnTypeName; 
  public int precision; 
  public int scale; 
  public String schemaName; 
  public String tableName; 
  public boolean autoIncrement; 
  public boolean caseSensitive; 
  public boolean currency; 
  public boolean definitelyWritable; 
  public int nullable; 
  public boolean readOnly; 
  public boolean searchable; 
  public boolean signed; 
  public boolean writable;


  public SerializableResultSetMetaDataPart(String catalogName, String columnClassName, int columnDisplaySize, String columnLabel, String columnName, int columnType, String columnTypeName, int precision, int scale, String schemaName, String tableName, boolean autoIncrement, boolean caseSensitive, boolean currency, boolean definitelyWritable, int nullable, boolean readOnly, boolean searchable, boolean signed, boolean writable) {
    this.catalogName = catalogName;
    this.columnClassName = columnClassName;
    this.columnDisplaySize = columnDisplaySize;
    this.columnLabel = columnLabel;
    this.columnName = columnName;
    this.columnType = columnType;
    this.columnTypeName = columnTypeName;
    this.precision = precision;
    this.scale = scale;
    this.schemaName = schemaName;
    this.tableName = tableName;
    this.autoIncrement = autoIncrement;
    this.caseSensitive = caseSensitive;
    this.currency = currency;
    this.definitelyWritable = definitelyWritable;
    this.nullable = nullable;
    this.readOnly = readOnly;
    this.searchable = searchable;
    this.signed = signed;
    this.writable = writable;
  }

  public String getCatalogName() {
    return catalogName;
  }

  public String getColumnClassName() {
    return columnClassName;
  }

  public int getColumnDisplaySize() {
    return columnDisplaySize;
  }

  public String getColumnLabel() {
    return columnLabel;
  }

  public String getColumnName() {
    return columnName;
  }

  public int getColumnType() {
    return columnType;
  }

  public String getColumnTypeName() {
    return columnTypeName;
  }

  public int getPrecision() {
    return precision;
  }

  public int getScale() {
    return scale;
  }

  public String getSchemaName() {
    return schemaName;
  }

  public String getTableName() {
    return tableName;
  }

  public boolean isAutoIncrement() {
    return autoIncrement;
  }

  public boolean isCaseSensitive() {
    return caseSensitive;
  }

  public boolean isCurrency() {
    return currency;
  }

  public boolean isDefinitelyWritable() {
    return definitelyWritable;
  }

  public int getNullable() {
    return nullable;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public boolean isSearchable() {
    return searchable;
  }

  public boolean isSigned() {
    return signed;
  }

  public boolean isWritable() {
    return writable;
  }



  public void setCatalogName(String catalogName) {
    this.catalogName = catalogName;
  }

  public void setColumnClassName(String columnClassName) {
    this.columnClassName = columnClassName;
  }

  public void setColumnDisplaySize(int columnDisplaySize) {
    this.columnDisplaySize = columnDisplaySize;
  }

  public void setColumnLabel(String columnLabel) {
    this.columnLabel = columnLabel;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public void setColumnType(int columnType) {
    this.columnType = columnType;
  }

  public void setColumnTypeName(String columnTypeName) {
    this.columnTypeName = columnTypeName;
  }

  public void setPrecision(int precision) {
    this.precision = precision;
  }

  public void setScale(int scale) {
    this.scale = scale;
  }

  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public void setAutoIncrement(boolean autoIncrement) {
    this.autoIncrement = autoIncrement;
  }

  public void setCaseSensitive(boolean caseSensitive) {
    this.caseSensitive = caseSensitive;
  }

  public void setCurrency(boolean currency) {
    this.currency = currency;
  }

  public void setDefinitelyWritable(boolean definitelyWritable) {
    this.definitelyWritable = definitelyWritable;
  }

  public void setNullable(int nullable) {
    this.nullable = nullable;
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  public void setSearchable(boolean searchable) {
    this.searchable = searchable;
  }

  public void setSigned(boolean signed) {
    this.signed = signed;
  }

  public void setWritable(boolean writable) {
    this.writable = writable;
  }

  public SerializableResultSetMetaDataPart() {

  }

}
