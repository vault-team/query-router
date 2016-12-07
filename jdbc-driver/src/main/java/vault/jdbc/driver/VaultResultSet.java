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

import vault.query.result.SerializableResultSet;
import vault.query.result.SerializableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class VaultResultSet implements ResultSet {

  final Logger logger = LoggerFactory.getLogger(VaultResultSet.class);

  private Queue<SQLWarning> warnings = new LinkedList<SQLWarning>();
  private SerializableResultSet resultset;
  private VaultResultSetMetaData metadata;
  private VaultStatement statement;

  private int type = ResultSet.TYPE_SCROLL_INSENSITIVE;
  private boolean wasNull = false;
  private boolean isClosed;
  private Iterator<SerializableRow> iterator = null;
  private SerializableRow curRow = null;

  private boolean last = false;
  private boolean first = true;
  private int rowIndex = 0;

  public VaultResultSet(SerializableResultSet resultset) {
    this.resultset = resultset;
    this.metadata = new VaultResultSetMetaData(resultset.getSerializableResultSetMetaData());
    this.iterator = this.resultset.getRows().iterator();
  }

  private Object getColumnValue(int columnIndex) throws SQLException {
    if (this.curRow == null) {
      throw new SQLException("No row found.");
    }
    if (columnIndex > this.getMetaData().getColumnCount()) {
      throw new SQLException("Invalid columnIndex: " + columnIndex);
    }

    return this.curRow.get(columnIndex);
  }

  public boolean isWrapperFor(Class<?> arg0) throws SQLException {
    if(arg0 == SerializableResultSet.class){
      return true;
    }
    return false;
  }


  public <T> T unwrap(Class<T> arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public boolean absolute(int arg0) throws SQLException {
    // TODO Auto-generated method stub
    return false;
  }


  public void afterLast() throws SQLException {
    // TODO Auto-generated method stub

  }


  public void beforeFirst() throws SQLException {
    // TODO Auto-generated method stub

  }


  public void cancelRowUpdates() throws SQLException {
    // TODO Auto-generated method stub

  }


  public void clearWarnings() throws SQLException {
    // TODO Provide warnings in ResultSet
  }


  public void close() throws SQLException {
    this.resultset = null;
    this.isClosed = true;
    this.statement = null;
  }


  public void deleteRow() throws SQLException {
    // TODO Auto-generated method stub

  }


  public int findColumn(String columnLabel) throws SQLException {
    int columnIndex = this.metadata.findColumn(columnLabel.toLowerCase());
    if (columnIndex == -1) {
      throw new SQLException();
    } else {
      return columnIndex;
    }
  }


  public boolean first() throws SQLException {
    return true;
  }


  public Array getArray(int arg0) throws SQLException {
    return (Array) getColumnValue(arg0);
  }


  public Array getArray(String arg0) throws SQLException {
    return getArray(findColumn(arg0));
  }


  public InputStream getAsciiStream(int arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public InputStream getAsciiStream(String arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public BigDecimal getBigDecimal(int arg0) throws SQLException {
    return (BigDecimal) getColumnValue(arg0);
  }


  public BigDecimal getBigDecimal(String arg0) throws SQLException {
    return getBigDecimal(findColumn(arg0));
  }


  public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
    return getBigDecimal(arg0).setScale(arg1);
  }


  public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
    return getBigDecimal(arg0).setScale(arg1);
  }


  public InputStream getBinaryStream(int arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public InputStream getBinaryStream(String arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public Blob getBlob(int arg0) throws SQLException {
    return (Blob) getColumnValue(arg0);
  }


  public Blob getBlob(String arg0) throws SQLException {
    return (Blob) getColumnValue(findColumn(arg0));
  }


  public boolean getBoolean(int columnIndex) throws SQLException {
    return (boolean) getColumnValue(columnIndex);
  }


  public boolean getBoolean(String columnLabel) throws SQLException {
    return getBoolean(findColumn(columnLabel));
  }


  public byte getByte(int arg0) throws SQLException {
    return (byte) getColumnValue(arg0);
  }


  public byte getByte(String arg0) throws SQLException {
    return getByte(findColumn(arg0));
  }


  public byte[] getBytes(int arg0) throws SQLException {
    return (byte[]) getColumnValue(arg0);
  }


  public byte[] getBytes(String arg0) throws SQLException {
    return getBytes(findColumn(arg0));
  }


  public Reader getCharacterStream(int arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public Reader getCharacterStream(String arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public Clob getClob(int arg0) throws SQLException {
    return (Clob) getColumnValue(arg0);
  }


  public Clob getClob(String arg0) throws SQLException {
    return getClob(findColumn(arg0));
  }


  public int getConcurrency() throws SQLException {
    throw new SQLException("Method not supported");
  }


  public String getCursorName() throws SQLException {
    throw new SQLException("Method not supported");
  }


  public Date getDate(int arg0) throws SQLException {
    return (Date) getColumnValue(arg0);
  }


  public Date getDate(String arg0) throws SQLException {
    return (Date) getColumnValue(findColumn(arg0));
  }


  public Date getDate(int arg0, Calendar arg1) throws SQLException {
    Date date = getDate(arg0);
    return new Date(date.getTime()+arg1.getTimeZone().getRawOffset());
  }


  public Date getDate(String arg0, Calendar arg1) throws SQLException {
    return getDate(findColumn(arg0), arg1);
  }


  public double getDouble(int columnIndex) throws SQLException {
    try {
      Number val = (Number) getColumnValue(columnIndex);
      return val.doubleValue();
    } catch (Exception e) {
      throw new SQLException(
        "Cannot convert column " + columnIndex + " to double: " + e.toString(),
        e);
    }
  }


  public double getDouble(String columnLabel) throws SQLException {
    return getDouble(findColumn(columnLabel));
  }


  public int getFetchDirection() throws SQLException {
    throw new SQLException("Method not supported");
  }


  public int getFetchSize() throws SQLException {
    throw new SQLException("Method not supported");
  }


  public float getFloat(int columnIndex) throws SQLException {
    try {
      Number val = (Number) getColumnValue(columnIndex);
      return val.floatValue();
    } catch (Exception e) {
      throw new SQLException(
        "Cannot convert column " + columnIndex + " to float: " + e.toString(),
        e);
    }
  }


  public float getFloat(String columnLabel) throws SQLException {
    return getFloat(findColumn(columnLabel));
  }


  public int getHoldability() throws SQLException {
    throw new SQLException("Method not supported");
  }


  public int getInt(int columnIndex) throws SQLException {
    try {
      Number val = (Number) getColumnValue(columnIndex);
      return val.intValue();
    } catch (Exception e) {
      throw new SQLException(
        "Cannot convert column " + columnIndex + " to int: " + e.toString(),
        e);
    }
  }


  public int getInt(String columnName) throws SQLException {
    return getInt(findColumn(columnName));
  }


  public long getLong(int columnIndex) throws SQLException {
    Number val = (Number) getColumnValue(columnIndex);
    return val.longValue();
  }

  public long getLong(String columnName) throws SQLException {
    return getLong(findColumn(columnName));
  }


  public ResultSetMetaData getMetaData() throws SQLException {
    return this.metadata;
  }


  public Reader getNCharacterStream(int arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public Reader getNCharacterStream(String arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public NClob getNClob(int arg0) throws SQLException {
    return (NClob) getColumnValue(arg0);
  }


  public NClob getNClob(String arg0) throws SQLException {
    return getNClob(findColumn(arg0));
  }


  public String getNString(int columnIndex) throws SQLException {
    return getString(columnIndex);
  }


  public String getNString(String columnLabel) throws SQLException {
    return getString(findColumn(columnLabel));
  }


  public Object getObject(int columnIndex) throws SQLException {
    return getColumnValue(columnIndex);
  }


  public Object getObject(String columnLabel) throws SQLException {
    return getObject(findColumn(columnLabel));
  }


  public Object getObject(int arg0, Map<String, Class<?>> arg1)
    throws SQLException {
    throw new SQLException("Method not supported");
  }


  public Object getObject(String arg0, Map<String, Class<?>> arg1)
    throws SQLException {
    throw new SQLException("Method not supported");
  }


  public <T> T getObject(int arg0, Class<T> arg1) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public <T> T getObject(String arg0, Class<T> arg1) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public Ref getRef(int arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public Ref getRef(String arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public int getRow() throws SQLException {
    return this.rowIndex;
  }


  public RowId getRowId(int arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public RowId getRowId(String arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public SQLXML getSQLXML(int arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public SQLXML getSQLXML(String arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public short getShort(int columnIndex) throws SQLException {
    try {
      Number val = (Number) getColumnValue(columnIndex);
      return val.shortValue();
    } catch (Exception e) {
      throw new SQLException(
        "Cannot convert column " + columnIndex + " to short: " + e.toString(),
        e);
    }
  }


  public short getShort(String columnLabel) throws SQLException {
    return getShort(findColumn(columnLabel));
  }


  public Statement getStatement() throws SQLException {
    return this.statement;
  }


  public String getString(int columnIndex) throws SQLException {
    Object value = getColumnValue(columnIndex);
    return value.toString();
  }


  public String getString(String columnName) throws SQLException {
    return getString(findColumn(columnName));
  }


  public Time getTime(int arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public Time getTime(String arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public Time getTime(int arg0, Calendar arg1) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public Time getTime(String arg0, Calendar arg1) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public Timestamp getTimestamp(int columnIndex) throws SQLException {
    return (Timestamp) getColumnValue(columnIndex);
  }


  public Timestamp getTimestamp(String columnLabel) throws SQLException {
    return getTimestamp(findColumn(columnLabel));
  }


  public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public Timestamp getTimestamp(String arg0, Calendar arg1)
    throws SQLException {
    throw new SQLException("Method not supported");
  }


  public int getType() throws SQLException {
    return this.type;
  }


  public URL getURL(int arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public URL getURL(String arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public InputStream getUnicodeStream(int arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public InputStream getUnicodeStream(String arg0) throws SQLException {
    throw new SQLException("Method not supported");
  }


  public SQLWarning getWarnings() throws SQLException {
    return this.warnings.poll();
  }


  public void insertRow() throws SQLException {
    // TODO Auto-generated method stub

  }


  public boolean isAfterLast() throws SQLException {
    return isLast();
  }


  public boolean isBeforeFirst() throws SQLException {
    return (getRow() == 0);
  }


  public boolean isClosed() throws SQLException {
    return this.isClosed;
  }


  public boolean isFirst() throws SQLException {
    return first;
  }


  public boolean isLast() throws SQLException {
    return last;
  }


  public boolean last() throws SQLException {
    throw new UnsupportedOperationException();
  }


  public void moveToCurrentRow() throws SQLException {
    // TODO Auto-generated method stub

  }


  public void moveToInsertRow() throws SQLException {
    // TODO Auto-generated method stub

  }


  public boolean next() throws SQLException {
    if(this.iterator.hasNext()) {
      this.curRow = this.iterator.next();
      first = false;
      rowIndex ++;
      return true;
    }
    last = true;
    return false;
  }


  public boolean previous() throws SQLException {
    throw new UnsupportedOperationException();
  }


  public void refreshRow() throws SQLException {
    // TODO Auto-generated method stub

  }


  public boolean relative(int arg0) throws SQLException {
    // TODO Auto-generated method stub
    return false;
  }


  public boolean rowDeleted() throws SQLException {
    // TODO Auto-generated method stub
    return false;
  }


  public boolean rowInserted() throws SQLException {
    // TODO Auto-generated method stub
    return false;
  }


  public boolean rowUpdated() throws SQLException {
    // TODO Auto-generated method stub
    return false;
  }


  public void setFetchDirection(int arg0) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void setFetchSize(int arg0) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateArray(int arg0, Array arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateArray(String arg0, Array arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateAsciiStream(int arg0, InputStream arg1)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateAsciiStream(String arg0, InputStream arg1)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateAsciiStream(int arg0, InputStream arg1, int arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateAsciiStream(String arg0, InputStream arg1, int arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateAsciiStream(int arg0, InputStream arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateAsciiStream(String arg0, InputStream arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBigDecimal(String arg0, BigDecimal arg1)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBinaryStream(int arg0, InputStream arg1)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBinaryStream(String arg0, InputStream arg1)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBinaryStream(int arg0, InputStream arg1, int arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBinaryStream(String arg0, InputStream arg1, int arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBinaryStream(int arg0, InputStream arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBinaryStream(String arg0, InputStream arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBlob(int arg0, Blob arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBlob(String arg0, Blob arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBlob(int arg0, InputStream arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBlob(String arg0, InputStream arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBlob(int arg0, InputStream arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBlob(String arg0, InputStream arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBoolean(int arg0, boolean arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBoolean(String arg0, boolean arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateByte(int arg0, byte arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateByte(String arg0, byte arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBytes(int arg0, byte[] arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateBytes(String arg0, byte[] arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateCharacterStream(int arg0, Reader arg1)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateCharacterStream(String arg0, Reader arg1)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateCharacterStream(int arg0, Reader arg1, int arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateCharacterStream(String arg0, Reader arg1, int arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateCharacterStream(int arg0, Reader arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateCharacterStream(String arg0, Reader arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateClob(int arg0, Clob arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateClob(String arg0, Clob arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateClob(int arg0, Reader arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateClob(String arg0, Reader arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateClob(int arg0, Reader arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateClob(String arg0, Reader arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateDate(int arg0, Date arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateDate(String arg0, Date arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateDouble(int arg0, double arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateDouble(String arg0, double arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateFloat(int arg0, float arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateFloat(String arg0, float arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateInt(int arg0, int arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateInt(String arg0, int arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateLong(int arg0, long arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateLong(String arg0, long arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNCharacterStream(int arg0, Reader arg1)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNCharacterStream(String arg0, Reader arg1)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNCharacterStream(int arg0, Reader arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNCharacterStream(String arg0, Reader arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNClob(int arg0, NClob arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNClob(String arg0, NClob arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNClob(int arg0, Reader arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNClob(String arg0, Reader arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNClob(int arg0, Reader arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNClob(String arg0, Reader arg1, long arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNString(int arg0, String arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNString(String arg0, String arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNull(int arg0) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateNull(String arg0) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateObject(int arg0, Object arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateObject(String arg0, Object arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateObject(int arg0, Object arg1, int arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateObject(String arg0, Object arg1, int arg2)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateRef(int arg0, Ref arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateRef(String arg0, Ref arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateRow() throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateRowId(int arg0, RowId arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateRowId(String arg0, RowId arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateShort(int arg0, short arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateShort(String arg0, short arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateString(int arg0, String arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateString(String arg0, String arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateTime(int arg0, Time arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateTime(String arg0, Time arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
    // TODO Auto-generated method stub

  }


  public void updateTimestamp(String arg0, Timestamp arg1)
    throws SQLException {
    // TODO Auto-generated method stub

  }


  public boolean wasNull() throws SQLException {
    return this.wasNull;
  }

}