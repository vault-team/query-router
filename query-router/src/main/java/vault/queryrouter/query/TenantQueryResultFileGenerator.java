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

package vault.queryrouter.query;


import com.esotericsoftware.kryo.io.Output;
import vault.common.util.KryoUtil;
import vault.query.result.ResultSetConverter;
import vault.queryrouter.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

public class TenantQueryResultFileGenerator {

  static public Logger logger = LoggerFactory.getLogger(TenantQueryResultFileGenerator.class);

  public static void generateCsvFile(ResultSet result, String queryId) throws Exception {

    String fileName;

    OutputStream outputStream = null;
    Output output = null;

    try {
      fileName = queryId + ".csv";
      outputStream = new FileOutputStream(Constant.DOWNLOAD_PATH + fileName);
      output = new Output(outputStream);

      KryoUtil.getInstance().writeObject(output, ResultSetConverter.convert(result));

    } catch (IOException e) {
      logger.error("Failed to to produce the CSV file", e);
      throw e;
    } finally {
      output.close();
    }

  }
}
