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

package vault.queryrouter.common;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.SystemConfiguration;

import java.util.MissingFormatArgumentException;
import java.util.UUID;

/**
 * System constant
 */
public class Constant {

  public static CompositeConfiguration configs = new CompositeConfiguration();

  static {
    //Init by system prop now, may be add prop list
    configs.addConfiguration(new SystemConfiguration());
  }

  // defines the csv file storage location for downloading.
  public static String DOWNLOAD_PATH = getTempDir() + "/download/";

  // defines the file storage location for uploading.
  public static String UPLOAD_PATH = getTempDir()+ "/upload/";

  // defines the driver name for establishing a database connection using JDBC. If the relational database management system (RDBMS) to be used is vertica, the JDBC driver name should be "com.vertica.Driver"
  public static String DRIVER = "com.vertica.Driver";

  // defines the URL format. If the RDBMS to be used is vertica, the URL format should be "jdbc:vertica://"
  public static String URL_HEADER = "jdbc:vertica://";

  // defines the port number of the host
  public static String URL_PORT = "5433";

  // defines the number of threads for select statement execution.
  public static int SELECT_THREAD_POOL_SIZE = 3;

  // defines the number of threads for update statement execution.
  public static int UPDATE_THREAD_POOL_SIZE = 3;

  // defines the name of the file returns from the polling request.
  // The file name should be consistent with the file name in the Constant.java of JDBC driver.
  public static String RETURN_PROCESSING_FILE_NAME = "Null"; //consistent to the jdbc-driver/common/Constant

  // defines a UUID for the worker to handling the query. The default value will be a string of random UUID.
  public static String WORKER_ID = configs.getString(ConfigKeys.KEY_WORKER_ID, UUID.randomUUID().toString());

  // the waiting time of each update statement execution thread
  public static Integer UPDATEJOB_CHECK_TIME_SECOND = new Integer(configs.getInt(ConfigKeys.KEY_UPDATEJOB_CHECK_TIME_SECOND, 5));

  // the name of the vertica database. The default vaule will be “db_srvr”
  public static String VERTICA_DEFAULT_DATABASE = configs.getString(ConfigKeys.KEY_VERTICA_DATABASE_NAME,"db_srvr");

  // the default schema in the vertica database. The default value will be “default” therefore the query will be executed within default schema during no schema is specified.
  public static String VAULT_TENANT_DEFAULT_SCHEMA = configs.getString(ConfigKeys.KEY_VAULT_TENANT_DEFAULT_SCHEMA,"default");

  public static String LISTEN_HOST = configs.getString(ConfigKeys.KEY_LISTEN_HOST,"0.0.0.0");

  public static int LISTEN_PORT = configs.getInt(ConfigKeys.KEY_LISTEN_PORT,8080);

  public static String getConfigDir(){
    return configs.getString(ConfigKeys.KEY_HOME_DIR)+"/conf";
  }

  public static String getTempDir(){
    return configs.getString(ConfigKeys.KEY_HOME_DIR)+"/tmp";
  }

  public static String getBackendDbConnString(){
    String host = getKeyConfigValue(ConfigKeys.KEY_BACKEND_DB_HOST);
    String port_str = getKeyConfigValue(ConfigKeys.KEY_BACKEND_DB_PORT);
    int port = Integer.valueOf(port_str);
    String schema = getKeyConfigValue(ConfigKeys.KEY_BACKEND_DB_SCHEMA);

    return String.format("jdbc:mysql://%s:%d/%s",host,port,schema);
  }

  public static String getBackendDbUsername(){
    return getKeyConfigValue(ConfigKeys.KEY_BACKEND_DB_USERNAME);
  }

  public static String getBackendDbPassword(){
    return getKeyConfigValue(ConfigKeys.KEY_BACKEND_DB_PASSWORD);
  }

  public static String getKeyConfigValue(String key){
    String value = configs.getString(key);
    if(value == null)
      throw new MissingFormatArgumentException("'"+key+"'"+" is not specific in the configuration file");
    return value;
  }

  public static class ConfigKeys {
    public static String KEY_HOME_DIR = "home.dir";
    public static String KEY_WORKER_ID = "worker.id";

    //REST API config
    public static String KEY_LISTEN_HOST = "listen.host";
    public static String KEY_LISTEN_PORT = "listen.port";

    //database specific configuration
    public static String KEY_BACKEND_DB_HOST = "backend.db.host";
    public static String KEY_BACKEND_DB_PORT = "backend.db.port";
    public static String KEY_BACKEND_DB_SCHEMA = "backend.db.schema";
    public static String KEY_BACKEND_DB_USERNAME = "backend.db.username";
    public static String KEY_BACKEND_DB_PASSWORD = "backend.db.password";

    // not expose to public yet
    public static String KEY_UPDATEJOB_CHECK_TIME_SECOND = "updatejob.check.time.second";
    public static String KEY_VERTICA_DATABASE_NAME = "vertica.database.name";
    public static String KEY_VAULT_TENANT_DEFAULT_SCHEMA = "vault.tenant.default.schema";

  }
}
