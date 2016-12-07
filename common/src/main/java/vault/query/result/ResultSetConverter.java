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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetConverter {

  public static SerializableResultSet convert(ResultSet rs) throws SQLException {
    RowIterable ri = new RowIterable(rs);
    return new SerializableResultSet(ri, convert(rs.getMetaData()));
  }

  public static SerializableResultSetMetaData convert(ResultSetMetaData resultSetMetaData) throws SQLException {
    int columnCount = resultSetMetaData.getColumnCount();
    List<SerializableResultSetMetaDataPart> parts = new ArrayList<>();

    for(int i=0;i<columnCount;i++){
      SerializableResultSetMetaDataPart newMetaData = new SerializableResultSetMetaDataPart(
        resultSetMetaData.getCatalogName(i+1),
        resultSetMetaData.getColumnClassName(i+1),
        resultSetMetaData.getColumnDisplaySize(i+1),
        resultSetMetaData.getColumnLabel(i+1),
        resultSetMetaData.getColumnName(i+1),
        resultSetMetaData.getColumnType(i+1),
        resultSetMetaData.getColumnTypeName(i+1),
        resultSetMetaData.getPrecision(i+1),
        resultSetMetaData.getScale(i+1),
        resultSetMetaData.getSchemaName(i+1),
        resultSetMetaData.getTableName(i+1),
        resultSetMetaData.isAutoIncrement(i+1),
        resultSetMetaData.isCaseSensitive(i+1),
        resultSetMetaData.isCurrency(i+1),
        resultSetMetaData.isDefinitelyWritable(i+1),
        resultSetMetaData.isNullable(i+1),
        resultSetMetaData.isReadOnly(i+1),
        resultSetMetaData.isSearchable(i+1),
        resultSetMetaData.isSigned(i+1),
        resultSetMetaData.isWritable(i+1)
      );
      parts.add(newMetaData);
    }

    return new SerializableResultSetMetaData(parts);
  }
}
