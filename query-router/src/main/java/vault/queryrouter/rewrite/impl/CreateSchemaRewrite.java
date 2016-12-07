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

package vault.queryrouter.rewrite.impl;

import vault.queryrouter.rewrite.SQLRewriteRule;
import net.sf.jsqlparser.JSQLParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringTokenizer;

/**
 * Rule to rewrite schema name when rewrite shcema
 * For example
 * SET search_path to test
 * is rewrite to
 * SET search_path to t1_test
 * where `1` is the tenant id
 */
public class CreateSchemaRewrite implements SQLRewriteRule {

  private Logger logger = LoggerFactory.getLogger(getClass());

  public String rewrite(RewriteContextInfo context, String sql) throws JSQLParserException {

    try {
      StringTokenizer st = new StringTokenizer(sql);
      int no_of_token = st.countTokens();
      String startsWith = st.nextToken();

      //rewrite "set search_path to"
      if (no_of_token >= 4 && startsWith.compareToIgnoreCase("SET") == 0) {
        if (st.nextToken().compareToIgnoreCase("search_path") == 0) {
          if (st.nextToken().compareToIgnoreCase("to") == 0) {
            return "SET search_path to " + StaticRewriteFunction.rewriteSchema(context.tenantMppdbId, st.nextToken());
          }
        }
        //rewrite "create/drop schema\table"
      } else if (startsWith.equalsIgnoreCase("CREATE") || startsWith.equalsIgnoreCase("DROP")) {
        logger.info("Create/Drop Statement");
        String property = st.nextToken();

        //CREATE SCHEMA IF NOT EXISTS schema;
        if (no_of_token >= 6 && property.equalsIgnoreCase("SCHEMA")) {
          String schemaName = "";
          for (int schemaNameFinder = 0; schemaNameFinder < 4; schemaNameFinder++)
            schemaName = st.nextToken();
          return "CREATE SCHEMA IF NOT EXISTS " + StaticRewriteFunction.rewriteSchema(context.tenantMppdbId, schemaName);
        }
        //CREATE SCHEMA schema; or DROP SCHEMA schema;
        else if (no_of_token == 3 && property.equalsIgnoreCase("SCHEMA")) {

          String oldSchemaName = st.nextToken();
          String newSchemaName = StaticRewriteFunction.rewriteSchema(context.tenantMppdbId, oldSchemaName);
          sql = sql.replace(oldSchemaName, newSchemaName);
        }
        //CREATE TABLE table... or DROP TABLE table...
        /*else if (property.equalsIgnoreCase("TABLE")) {
          if (startsWith.equalsIgnoreCase("CREATE")) {
            CreateTable parseStatement = (CreateTable) CCJSqlParserUtil.parse(sql);

            Table table = parseStatement.getTable();
            String oldSchemaName = table.getSchemaName();
            String newSchemaName = StaticRewriteFunction.rewriteSchema(context.tenantMppdbId, oldSchemaName);
            sql = sql.replace(oldSchemaName, newSchemaName);
          } else if (startsWith.equalsIgnoreCase("DROP")) {

            String oldSchemaName = st.nextToken();

            if (oldSchemaName.contains(".")) {
              String newSchemaName = StaticRewriteFunction.rewriteSchema(context.tenantMppdbId, oldSchemaName);
              sql = sql.replace(oldSchemaName, newSchemaName);
            } else
              return sql;
          }
        }
        */
      }
      /*else if(startsWith.equalsIgnoreCase("UPDATE")){
        logger.info("Update Statement");

        Update parseStatement = (Update) CCJSqlParserUtil.parse(sql);

        Table table = parseStatement.getTable();
        String oldSchemaName = table.getSchemaName();
        String newSchemaName = StaticRewriteFunction.rewriteSchema(context.tenantMppdbId, oldSchemaName);

        table.setSchemaName(newSchemaName);

        sql = parseStatement.toString();
      }
      else if(startsWith.equalsIgnoreCase("INSERT")){
        logger.info("Insert Statement");

        Insert parseStatement = (Insert) CCJSqlParserUtil.parse(sql);

        Table table = parseStatement.getTable();
        String oldSchemaName = table.getSchemaName();
        String newSchemaName = StaticRewriteFunction.rewriteSchema(context.tenantMppdbId, oldSchemaName);

        table.setSchemaName(newSchemaName);

        sql = parseStatement.toString();
      }
      else if(startsWith.equalsIgnoreCase("DELETE")){
        logger.info("Delete Statement");

        Delete parseStatement = (Delete) CCJSqlParserUtil.parse(sql);

        Table table = parseStatement.getTable();
        String oldSchemaName = table.getSchemaName();

        String newSchemaName = StaticRewriteFunction.rewriteSchema(context.tenantMppdbId, oldSchemaName);
        table.setSchemaName(newSchemaName);

        sql = parseStatement.toString();
      }

      else if(startsWith.equalsIgnoreCase("REPLACE")){
        logger.info("Replace Statement");

        Replace parseStatement = (Replace) CCJSqlParserUtil.parse(sql);

        Table table = parseStatement.getTable();
        String oldSchemaName = table.getSchemaName();

        String newSchemaName = StaticRewriteFunction.rewriteSchema(context.tenantMppdbId, oldSchemaName);

        table.setSchemaName(newSchemaName);

        sql = parseStatement.toString();
      }
      else if(startsWith.equalsIgnoreCase("SELECT")){
        logger.info("Select Statement");

        Select parseStatement = (Select) CCJSqlParserUtil.parse(sql);
        SelectBody selectBody = parseStatement.getSelectBody();
        FromItem fromItem = ((PlainSelect)selectBody).getFromItem();
        String oldSchemaName = ((Table)fromItem).getSchemaName();

        String newSchemaName = StaticRewriteFunction.rewriteSchema(context.tenantMppdbId, oldSchemaName);

        ((Table) fromItem).setSchemaName(newSchemaName);

        sql = parseStatement.toString();
      }

      //COPY Market From LOCAL '/Users/Julian/Desktop/consumer.csv' DELIMITER ',';
      else if(startsWith.equalsIgnoreCase("COPY")){
        String oldSchemaName = st.nextToken();

        if(oldSchemaName.contains(".")) {
          String newSchemaName = StaticRewriteFunction.rewriteSchema(context.tenantMppdbId, oldSchemaName);
          sql = sql.replace(oldSchemaName, newSchemaName);
        }
        else
          return sql;
      }

*/

    } catch (Exception e) {
      logger.error("Fail to rewrite sql: " + sql,e);
      throw e;
    }
    return sql;
  }
}
