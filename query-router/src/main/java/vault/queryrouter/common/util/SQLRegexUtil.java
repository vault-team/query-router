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

package vault.queryrouter.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Some regular expression for matching sql
 */
public class SQLRegexUtil {

  public static Pattern pattern_create_schema = Pattern.compile("^\\s*CREATE\\s+SCHEMA\\s*(IF\\s+NOT\\s+EXISTS)?\\s+(\\w+\\.)?(?<schema>\\w+)\\s*(AUTHORIZATION\\s+(?<username>\\w+))?$", Pattern.CASE_INSENSITIVE);
  public static Pattern pattern_set_search_path = Pattern.compile("^\\s*SET\\s+SEARCH_PATH\\s*(TO|=)\\s+(?<schema>\\w+)\\s*$", Pattern.CASE_INSENSITIVE);
  public static Pattern pattern_copy_local = Pattern.compile("^\\s*COPY\\s+(?<schema>\\S+)\\s+FROM\\s+LOCAL\\s+[\'|\"](?<localfileurl>[^\"\']*)[\'|\"].*$", Pattern.CASE_INSENSITIVE);

  public static boolean isCreateSchemaStatement(String sql){
    return pattern_create_schema.matcher(sql).matches();
  }

  public static String getSchemaFromCreateSchemaStatement(String sql){
    Matcher m = pattern_create_schema.matcher(sql);
    m.matches();
    return m.group("schema");
  }

  public static String getSchemaFromSetSearchPath(String sql){
    Matcher m = pattern_set_search_path.matcher(sql);
    m.matches();
    return m.group("schema");
  }

  public static String getLocalFilePathFromCOPYStatement(String sql){
    Matcher m = pattern_copy_local.matcher(sql);
    m.matches();
    String local_file_url = m.group("localfileurl");
    return local_file_url;
  }

}
