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
import java.sql.SQLException;
import java.util.Iterator;

public class RowIterable implements Iterable<SerializableRow> {

  private ResultSet rs;
  private Boolean isNext = false;
  private Boolean isRead = false;

  public RowIterable(ResultSet rs) {
    this.rs = rs;
  }

  @Override
  public Iterator<SerializableRow> iterator() {
    return new RowIterator(this.rs);
  }

  private class RowIterator implements Iterator<SerializableRow> {

    private ResultSet rs;
    private int columnCount;
    private int[] columnTypes;

    public RowIterator(ResultSet rs){
      this.rs = rs;
      try {
        columnCount = rs.getMetaData().getColumnCount();
        columnTypes = new int[columnCount+1];
        for(int i=0;i<columnCount;i++){
          columnTypes[i+1] = rs.getMetaData().getColumnType(i+1);
        }
      }catch (SQLException e){
        throw new RuntimeException(e);
      }
    }

    @Override
    public boolean hasNext() {
      try {
        if(isRead == false && isNext == true)
          return true;
        isNext = rs.next();
        if(isNext == true)
          isRead = false;
        return isNext;
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public SerializableRow next() {
      if(isNext==true && isRead==false) {
        isRead = true;
        return this.transformToArray(rs);
      }
      else{
        if (hasNext() == false)
          return null;
        else
          return this.transformToArray(rs);
      }
    }

    public Object transformValue(int columnIndex) throws SQLException {
      return rs.getObject(columnIndex);
    }

    public SerializableRow transformToArray(ResultSet set){
      Object[] result = new Object[this.columnCount];
      try {
        for (int i=0; i < this.columnCount; i++) {
          result[i] = transformValue(i+1);
        }
      }catch (SQLException e){
        throw new RuntimeException(e);
      }
      return new SerializableRow(result);
    }

    @Override
    public void remove() {
      try {
        this.rs.deleteRow();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
