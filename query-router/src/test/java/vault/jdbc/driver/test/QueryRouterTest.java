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

package vault.jdbc.driver.test;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class QueryRouterTest extends TestCase {

  static final String JDBC_DRIVER = "vault.jdbc.driver.VaultDriver";
  static final String connectionString = "jdbc:vault://192.168.1.138:8080/db_srvr/8";
  static final String USER = "admin";
  static final String PASSWORD = "D4U0R5I73O";

  /**
   * Test Case: 1,2,3,6,7,10,11,14,15,18,19,22,27,30,31,32,33,34,35
   *
   * @throws Exception
   */

  public void testCopyStatement() throws Exception {
    Connection conn = null;
    Statement stmt = null;

    // Step 1: Load the JDBC driver.
    Class.forName(JDBC_DRIVER);
    //CONNECTION_STRING, USER_NAME, PASSWORD
    conn = DriverManager.getConnection(connectionString, USER, PASSWORD);
    stmt = conn.createStatement();

    stmt.executeUpdate("CREATE TABLE Market(Date Date, Name varchar(64), PTID integer, LBMP numeric, Marginal_cost_losses numeric, Marginal_cost_congestion integer)");
    stmt.execute("COPY Market From LOCAL '/Users/Julian/Desktop/sample.txt' DELIMITER '\t'");

    ResultSet rs = stmt.executeQuery("Select * FROM Market");

    //stmt.executeUpdate("DROP TABLE Market");
  }

/*

  public void testACreateSchema() throws Exception {
    verticaClient.executeUpdate("CREATE SCHEMA schema_vault");
    verticaClient.executeUpdate("DROP TABLE Market");
  }


  public void testBSetSearchPath() throws Exception {
    verticaClient.executeUpdate("CREATE SCHEMA schema_vault");
    verticaClient.executeUpdate("SET SEARCH_PATH TO schema_vault");
    verticaClient.executeUpdate("DROP TABLE Market");
  }



  public void testDCreateTableStatement() throws Exception {
    verticaClient.executeUpdate("CREATE SCHEMA schema_vault");
    verticaClient.executeUpdate("SET SEARCH_PATH TO schema_vault");
    verticaClient.executeUpdate("CREATE TABLE Market(consumer_id INTEGER not NULL,consumer_name VARCHAR(255), consumer_income INTEGER, PRIMARY KEY ( consumer_id ))");
    verticaClient.executeUpdate("INSERT INTO Market " + "VALUES (1, 'Johnathon', 1000);");
    verticaClient.executeUpdate("INSERT INTO Market " + "VALUES (2, 'Peter', 2000);");
    verticaClient.executeUpdate("INSERT INTO Market " + "VALUES (3, 'Jane', 3000);");
    verticaClient.executeUpdate("DROP TABLE Market");
  }

  public void testFSelectStatement() throws Exception {
    int round = 0;
    ResultSet rs = verticaClient.executeQuery("Select * FROM CUSTOMER WHERE C_NAME='Customer#000000001'");
    while (rs.next()) {

      if (round == 0) {
        assertEquals(1, rs.getInt(1));

      }
      round++;

    }

  }
*/

}
