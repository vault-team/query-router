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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Functions to do some rewrite
 */
public class StaticRewriteFunction {

  public static String rewriteSchema(int tenantMppdbId, String schema_name) {
    return "tm" + tenantMppdbId + "_" + schema_name;
  }

  public static String rewriteUserName(int tenantMppdbId, String user_name) {
    return "tm" + tenantMppdbId + "_" + user_name;
  }

  public static String getSchemaFromRewroteSchema(String schema){
    Pattern p = Pattern.compile("^tm\\d+_(?<name>\\w+)$");
    Matcher m = p.matcher(schema);
    m.matches();
    return  m.group("name");
  }
}
