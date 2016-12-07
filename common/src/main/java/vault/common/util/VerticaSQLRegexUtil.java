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

package vault.common.util;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Strore regex for vertica SQL
 */
public class VerticaSQLRegexUtil {

  public static Pattern errorMessagePattern = Pattern.compile("^\\[\\w+\\]\\[\\w+\\]\\((?<sqlstatus>\\w+)\\)\\s+(?<message>.*)$");

  public static String getErrorMessage(String sqlstate, String message){
    return String.format("[Vault][Custom](%s) ERROR: %s",sqlstate, message);
  }

  public static SQLException getSQLExceptionFromMessage(String full_error_msg) throws Exception {
    try {
      Matcher m = VerticaSQLRegexUtil.errorMessagePattern.matcher(full_error_msg);
      m.matches();
      String error_code = m.group("sqlstatus");
      return new SQLException(full_error_msg, error_code);
    }catch (IllegalStateException e){
      //Not Vertica SQL exeption
      throw new Exception(full_error_msg);
    }
  }
}
