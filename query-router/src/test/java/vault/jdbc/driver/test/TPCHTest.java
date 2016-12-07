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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Suppose this file is run on testing server
 */
public class TPCHTest {

  public String data_file = "/Users/Julian/Downloads/tpch-dbgen";
  public Logger logger = LoggerFactory.getLogger(TPCHTest.class);
  Connection conn = null;
  Statement stmt = null;
  static final String JDBC_DRIVER = "VaultDriver";
  static final String connectionString = "jdbc:vault://192.168.1.138:8080/db_srvr/1";
  static final String USER = "Julian";
  static final String PASSWORD = "julian123";

  public TPCHTest(){
    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(connectionString, USER, PASSWORD);
      stmt = conn.createStatement();
    } catch(Exception e){
        logger.error("Cannot create connection", e);
    }
  }

  public void upload_data(){
    try {
      //CREATE SCHEMA
      for (String table : tableNames) {
        stmt.execute(get_drop_table_sql(table));
      }
      for (String sql : createTableSqls) {
        stmt.execute(sql);
      }

      for (String table : tableNames) {
        stmt.execute(get_copy_data_sql(table));
      }
    } catch(Exception e){
      logger.error("Cannot update data", e);
    }
  }

  public boolean check_table_exist(){
    try {
      stmt.executeQuery("SELECT * from LINEITEM limit 1");
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public void query(){
    try {
      String sql = "select l_returnflag from lineitem limit 1";
      String sql1 = "select\n" +
              "\tl_returnflag,\n" +
              "\tl_linestatus,\n" +
              "\tsum(l_quantity) as sum_qty,\n" +
              "\tsum(l_extendedprice) as sum_base_price,\n" +
              "\tsum(l_extendedprice * (1 - l_discount)) as sum_disc_price,\n" +
              "\tsum(l_extendedprice * (1 - l_discount) * (1 + l_tax)) as sum_charge,\n" +
              "\tavg(l_quantity) as avg_qty,\n" +
              "\tavg(l_extendedprice) as avg_price,\n" +
              "\tavg(l_discount) as avg_disc,\n" +
              "\tcount(*) as count_order\n" +
              "from\n" +
              "\tlineitem\n" +
              "where\n" +
              "\tl_shipdate <= date '1998-12-01' - interval ':1' day (3)\n" +
              "group by\n" +
              "\tl_returnflag,\n" +
              "\tl_linestatus\n" +
              "order by\n" +
              "\tl_returnflag,\n" +
              "\tl_linestatus";
      ResultSet rs = stmt.executeQuery(sql);

      logger.info("Execute");
    } catch(Exception e){
      logger.error("Can't right through the query",e);
    }

  }

  public void run(){
    try {
      //execute query
      query();
    }catch (Exception e){
      logger.error("Can't right through the test",e);
    }
    finally {
      try {
        stmt.close();
      } catch (SQLException e){
        logger.error("Can't right through the test",e);
      }
    }
  }

  public String get_copy_data_sql(String tablename){
    String filePath = data_file+"/"+tablename.toLowerCase()+".tbl";
    return String.format("COPY %s FROM LOCAL '%s' DELIMITER '|'",tablename.toUpperCase(), filePath);
  }

  public String get_drop_table_sql(String tablename){
    return String.format("DROP TABLE IF EXISTS %s", tablename);
  }

  public String[] tableNames = {
          "customer", "orders", "lineitem", "nation", "partsupp", "part", "region", "supplier"
  };

  public String[] createTableSqls = {
          sql_create_table_nation, sql_create_table_region, sql_create_table_part, sql_create_table_supplier, sql_create_table_partsupp,
          sql_create_table_customer, sql_create_table_order, sql_create_lineItem
  };

  static String sql_create_table_nation = "CREATE TABLE NATION  ( N_NATIONKEY  INTEGER NOT NULL,\n" +
          "                            N_NAME       CHAR(25) NOT NULL,\n" +
          "                            N_REGIONKEY  INTEGER NOT NULL,\n" +
          "                            N_COMMENT    VARCHAR(152))";
  static String sql_create_table_region = "CREATE TABLE REGION  ( R_REGIONKEY  INTEGER NOT NULL,\n" +
          "                            R_NAME       CHAR(25) NOT NULL,\n" +
          "                            R_COMMENT    VARCHAR(152))";
  static String sql_create_table_part = "CREATE TABLE PART  ( P_PARTKEY     INTEGER NOT NULL,\n" +
          "                          P_NAME        VARCHAR(55) NOT NULL,\n" +
          "                          P_MFGR        CHAR(25) NOT NULL,\n" +
          "                          P_BRAND       CHAR(10) NOT NULL,\n" +
          "                          P_TYPE        VARCHAR(25) NOT NULL,\n" +
          "                          P_SIZE        INTEGER NOT NULL,\n" +
          "                          P_CONTAINER   CHAR(10) NOT NULL,\n" +
          "                          P_RETAILPRICE DECIMAL(15,2) NOT NULL,\n" +
          "                          P_COMMENT     VARCHAR(23) NOT NULL )";
  static String sql_create_table_supplier = "CREATE TABLE SUPPLIER ( S_SUPPKEY     INTEGER NOT NULL,\n" +
          "                             S_NAME        CHAR(25) NOT NULL,\n" +
          "                             S_ADDRESS     VARCHAR(40) NOT NULL,\n" +
          "                             S_NATIONKEY   INTEGER NOT NULL,\n" +
          "                             S_PHONE       CHAR(15) NOT NULL,\n" +
          "                             S_ACCTBAL     DECIMAL(15,2) NOT NULL,\n" +
          "                             S_COMMENT     VARCHAR(101) NOT NULL)";
  static String sql_create_table_partsupp = "CREATE TABLE PARTSUPP ( PS_PARTKEY     INTEGER NOT NULL,\n" +
          "                             PS_SUPPKEY     INTEGER NOT NULL,\n" +
          "                             PS_AVAILQTY    INTEGER NOT NULL,\n" +
          "                             PS_SUPPLYCOST  DECIMAL(15,2)  NOT NULL,\n" +
          "                             PS_COMMENT     VARCHAR(199) NOT NULL )";
  static String sql_create_table_customer = "CREATE TABLE CUSTOMER ( C_CUSTKEY     INTEGER NOT NULL,\n" +
          "                             C_NAME        VARCHAR(25) NOT NULL,\n" +
          "                             C_ADDRESS     VARCHAR(40) NOT NULL,\n" +
          "                             C_NATIONKEY   INTEGER NOT NULL,\n" +
          "                             C_PHONE       CHAR(15) NOT NULL,\n" +
          "                             C_ACCTBAL     DECIMAL(15,2)   NOT NULL,\n" +
          "                             C_MKTSEGMENT  CHAR(10) NOT NULL,\n" +
          "                             C_COMMENT     VARCHAR(117) NOT NULL)";
  static String sql_create_table_order = "CREATE TABLE ORDERS  ( O_ORDERKEY       INTEGER NOT NULL,\n" +
          "                           O_CUSTKEY        INTEGER NOT NULL,\n" +
          "                           O_ORDERSTATUS    CHAR(1) NOT NULL,\n" +
          "                           O_TOTALPRICE     DECIMAL(15,2) NOT NULL,\n" +
          "                           O_ORDERDATE      DATE NOT NULL,\n" +
          "                           O_ORDERPRIORITY  CHAR(15) NOT NULL,  \n" +
          "                           O_CLERK          CHAR(15) NOT NULL, \n" +
          "                           O_SHIPPRIORITY   INTEGER NOT NULL,\n" +
          "                           O_COMMENT        VARCHAR(79) NOT NULL)";
  static String sql_create_lineItem = "CREATE TABLE LINEITEM ( L_ORDERKEY    INTEGER NOT NULL,\n" +
          "                             L_PARTKEY     INTEGER NOT NULL,\n" +
          "                             L_SUPPKEY     INTEGER NOT NULL,\n" +
          "                             L_LINENUMBER  INTEGER NOT NULL,\n" +
          "                             L_QUANTITY    DECIMAL(15,2) NOT NULL,\n" +
          "                             L_EXTENDEDPRICE  DECIMAL(15,2) NOT NULL,\n" +
          "                             L_DISCOUNT    DECIMAL(15,2) NOT NULL,\n" +
          "                             L_TAX         DECIMAL(15,2) NOT NULL,\n" +
          "                             L_RETURNFLAG  CHAR(1) NOT NULL,\n" +
          "                             L_LINESTATUS  CHAR(1) NOT NULL,\n" +
          "                             L_SHIPDATE    DATE NOT NULL,\n" +
          "                             L_COMMITDATE  DATE NOT NULL,\n" +
          "                             L_RECEIPTDATE DATE NOT NULL,\n" +
          "                             L_SHIPINSTRUCT CHAR(25) NOT NULL,\n" +
          "                             L_SHIPMODE     CHAR(10) NOT NULL,\n" +
          "                             L_COMMENT      VARCHAR(44) NOT NULL)";

  public static void main(String[] args){
    //TPCHTest test = new TPCHTest("jdbc:vault://127.0.0.1:8080/db_srvr/1", "Peter", "peter123");
    //TPCHTest test1 = new TPCHTest("jdbc:vault://127.0.0.1:8080/db_srvr/2", "Henry", "henry123");
    TPCHTest test2 = new TPCHTest();

    //TPCHTest test3 = new TPCHTest("jdbc:vault://127.0.0.1:8080/db_srvr/4", "Sally", "sally123");
    //TPCHTest test4 = new TPCHTest("jdbc:vault://127.0.0.1:8080/db_srvr/5", "Mary", "mary123");
    //TPCHTest test5 = new TPCHTest("jdbc:vault://127.0.0.1:8080/db_srvr/6", "Michael", "michael123");


    /*if(args.length > 0) {
      if (args[0].equals("upload"))
        test.upload_data();
      else
        test.run();
    }else
      test.run();

    if(args.length > 0) {
      if (args[0].equals("upload"))
        test1.upload_data();
      else
        test1.run();
    }else
      test1.run();
*/
    if(args.length > 0) {
      if (args[0].equals("upload"))
        test2.upload_data();
      else
        test2.run();
    }else
      test2.run();
/*

    if(args.length > 0) {
      if (args[0].equals("upload"))
        test3.upload_data();
      else
        test3.run();
    }else
      test3.run();

    if(args.length > 0) {
      if (args[0].equals("upload"))
        test4.upload_data();
      else
        test4.run();
    }else
      test4.run();

    if(args.length > 0) {
      if (args[0].equals("upload"))
        test5.upload_data();
      else
        test5.run();
    }else
      test5.run();
*/
  }
}