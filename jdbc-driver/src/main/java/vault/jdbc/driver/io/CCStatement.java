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

package vault.jdbc.driver.io;


public class CCStatement {

  public String sql;

  public CCStatement() {
  }

  public CCStatement(String sql, String database, int tenantid) {
    this();
    this.sql = sql;
  }

  /*
  public CCStatement(CCStatement other) {
    if (other.isSetSql()) {
      this.sql = other.sql;
    }
    this.id_connection = other.id_connection;
  }

  public CCStatement deepCopy() {
    return new CCStatement(this);
  }


  public int getId() {
    return this.id;
  }

  public CCStatement setId(int id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }
*/

  public String getSql() {
    return this.sql;
  }

  public CCStatement setSql(String sql) {
    this.sql = sql;
    return this;
  }

  public void unsetSql() {
    this.sql = null;
  }

  /**
   * Returns true if field sql is set (has been assigned a value) and false otherwise
   */
  public boolean isSetSql() {
    return this.sql != null;
  }

  public void setSqlIsSet(boolean value) {
    if (!value) {
      this.sql = null;
    }
  }

  /*
  public int getId_connection() {
    return this.id_connection;
  }

  public boolean equals(CCStatement that) {
    if (that == null)
      return false;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_sql = true && this.isSetSql();
    boolean that_present_sql = true && that.isSetSql();
    if (this_present_sql || that_present_sql) {
      if (!(this_present_sql && that_present_sql))
        return false;
      if (!this.sql.equals(that.sql))
        return false;
    }

    boolean this_present_id_connection = true;
    boolean that_present_id_connection = true;
    if (this_present_id_connection || that_present_id_connection) {
      if (!(this_present_id_connection && that_present_id_connection))
        return false;
      if (this.id_connection != that.id_connection)
        return false;
    }

    return true;
  }
  */


}
