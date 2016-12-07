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

package vault.jdbc.driver.common;


public class Constant {

  // the time for polling the ResultSet object returned from the query.
  public static int pollingTime = 1000 ;

  // defines the name of the file returns from the polling request.
  // the file name should be consistent with the file name in the Constant.java of Query Router.
  public static String RETURN_PROCESSING_FILE_NAME = "Null";
}
