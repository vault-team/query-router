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

import com.esotericsoftware.kryo.io.Input;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import vault.common.models.ConnectionRequest;
import vault.common.models.HTTPJsonResponse;
import vault.common.models.PollRequest;
import vault.common.util.JacksonHelper;
import vault.common.util.KryoUtil;
import vault.common.util.VerticaSQLRegexUtil;
import vault.jdbc.driver.common.Constant;
import vault.jdbc.driver.json.QueryRequest;
import vault.query.result.SerializableResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.StringTokenizer;


public class HttpClient {

  private static Logger logger = LoggerFactory.getLogger(HttpClient.class);
  private ObjectMapper om = JacksonHelper.getObjectMapper();
  SerializableResultSet resultSet;

  public SerializableResultSet statement_executeQuery(CCConnection conn, String sql) throws Exception {

    QueryRequest query = new QueryRequest(sql, conn.getMppdbId(), conn.getConnectionId(), conn.getUserId(), conn.getTenantMppdbId(), "executeQuery");

    String fileName = doPostQuery(conn.getIp(), query, "utf-8");
    if (fileName.equals("Failure")) {
      throw new SQLException("Fail to execute query: "+sql);
    }

    return recv_statement_executeQuery(conn, fileName);
  }

  public void statement_executeUpdate(CCConnection conn, String sql) throws Exception {
    QueryRequest query = new QueryRequest(sql, conn.getMppdbId(), conn.getConnectionId(), conn.getUserId(), conn.getTenantMppdbId(), "executeUpdate");

    doPostQuery(conn.getIp(), query, "utf-8");
  }

  public boolean statement_execute(CCConnection conn, String sql) throws Exception {

    StringTokenizer tokenizer = new StringTokenizer(sql);
    String startsWith = tokenizer.nextToken();

    if (startsWith.equalsIgnoreCase("COPY")) {

      String temp_local_path = null;
      for (int local_path_finder = 0; local_path_finder < 4; local_path_finder++)
        temp_local_path = tokenizer.nextToken();
      String local_path = temp_local_path.substring(1, temp_local_path.length() - 1);

      String fileName = uploadCsvFile(conn, local_path);

      QueryRequest query = new QueryRequest(sql, conn.getMppdbId(), conn.getConnectionId(), conn.getUserId(), conn.getTenantMppdbId(), "execute");
      query.setFileName(fileName);
      doPostQuery(conn.getIp(), query, "utf-8");

      return false;
    } else if (startsWith.equalsIgnoreCase("SELECT")) {
      QueryRequest query = new QueryRequest(sql, conn.getMppdbId(), conn.getConnectionId(), conn.getUserId(), conn.getTenantMppdbId(), "execute");
      String fileName = doPostQuery(conn.getIp(), query, "utf-8");
      resultSet = recv_statement_executeQuery(conn, fileName);
      return true;
    } else {
      QueryRequest query = new QueryRequest(sql, conn.getMppdbId(), conn.getConnectionId(), conn.getUserId(), conn.getTenantMppdbId(), "execute");
      doPostQuery(conn.getIp(), query, "utf-8");
      return false;
    }

  }

  public SerializableResultSet statement_getResultSet() {
    return resultSet;
  }

  public void create_connection(CCConnection conn) throws Exception {

    String connectionResult = doPostConnection(conn, null, null, "utf-8");

    JsonNode jsonObject = om.readTree(connectionResult);

    String connectionId = jsonObject.get("connectionId").textValue();

    logger.debug(connectionId);
    int userId = jsonObject.get("userId").intValue();
    int tenantMppdbId = jsonObject.get("tenantMppdbId").intValue();
    String mppdbId = jsonObject.get("mppdbId").textValue();

    conn.setConnectionId(connectionId);
    conn.setUserId(userId);
    conn.setTenantMppdbId(tenantMppdbId);
    conn.setMppdbId(mppdbId);

  }

  public void close_connection(CCConnection conn) throws Exception {
    doPostDisconnection(conn, null, null, "utf-8");

  }

  public SerializableResultSet recv_statement_executeQuery(CCConnection conn, String fileName) throws Exception {

    File downloadFile = downloadCsvFile(conn, fileName);
    InputStream fileInputStream = null;
    Input kryoInput = null;
    SerializableResultSet ret = null;

    try {
      fileInputStream = new FileInputStream(downloadFile);
      kryoInput = new Input(fileInputStream);
      ret = KryoUtil.getInstance().readObject(new Input(fileInputStream), SerializableResultSet.class);
      return ret;
    } catch (Exception e) {
      throw e;
    } finally {
      kryoInput.close();
    }

  }

  public File downloadCsvFile(CCConnection conn, String fileName) throws Exception {

    File tempFile = null;
    int BUFFER_SIZE = 4096;

    try {
      URL url = new URL("http://" + conn.getIp() + "/fileservlet/downloadfileservlet?fileName=" + fileName);

      HttpURLConnection URLConn = (HttpURLConnection) url.openConnection();

      URLConn.addRequestProperty("fileName", fileName);

      URLConn.setDoOutput(true);
      URLConn.setDoInput(true);
      ((HttpURLConnection) URLConn).setRequestMethod("GET");
      URLConn.setUseCaches(false);
      URLConn.setAllowUserInteraction(true);
      HttpURLConnection.setFollowRedirects(true);
      URLConn.setInstanceFollowRedirects(true);

      URLConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

      URLConn.connect();

      InputStream inputStream = URLConn.getInputStream();
      tempFile = File.createTempFile(fileName, ".csv");

      FileOutputStream outputStream = new FileOutputStream(tempFile);

      int bytesRead = -1;
      byte[] buffer = new byte[BUFFER_SIZE];
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }


      outputStream.close();
      inputStream.close();

    } catch (java.io.IOException e) {
      logger.error("Failed to download the CSV file", e);
      throw e;
    }

    return tempFile;
  }

  public String uploadCsvFile(CCConnection conn, String local_path) throws Exception {

    final int BUFFER_SIZE = 4096;
    String fileName = null;
    String returnFileName = null;

    try {

      File uploadFile = new File(local_path);

      // creates a HTTP connection
      URL url = new URL("http://" + conn.getIp() + "/fileservlet/uploadfileservlet");
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setChunkedStreamingMode(1024 * 1024 * 10);
      httpConn.setUseCaches(false);
      httpConn.setDoOutput(true);
      httpConn.setRequestMethod("POST");

      fileName = uploadFile.getName();
      // sets file name as a HTTP header
      httpConn.setRequestProperty("fileName", fileName);
      httpConn.setRequestProperty("connectionId", conn.connectionId);
      // opens output stream of the HTTP connection for writing data
      OutputStream outputStream = httpConn.getOutputStream();

      // Opens input stream of the file for reading data
      FileInputStream inputStream = new FileInputStream(uploadFile);

      byte[] buffer = new byte[BUFFER_SIZE];
      int bytesRead = -1;

      //Start writing data
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }

      outputStream.close();
      inputStream.close();

      // always check HTTP response code from server
      int responseCode = httpConn.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        // reads server's response
        BufferedReader reader = new BufferedReader(new InputStreamReader(
          httpConn.getInputStream()));
        returnFileName = reader.readLine();
        System.out.println("Server's response: file name is " + returnFileName);
      } else {
        System.out.println("Server returned non-OK code: " + responseCode);
      }

    } catch (FileNotFoundException e) {
      logger.error("Cannot find the file in the specified local path", e);
      throw e;
    } catch (java.io.IOException e) {
      logger.error("Failed to download the CSV file", e);
      throw e;
    }
    return returnFileName;
  }

  public String doPostQuery(String ip, QueryRequest query, String charset) throws Exception {

    JsonNode jsonObjectQuery = om.valueToTree(query);

    String fileName = null;

    URL url = new URL("http://" + ip + "/jsonservlet/query");

    //send vault.jdbc.driver.json query with user info.
    String queryResult = sendJsonRequest(charset, url, jsonObjectQuery);


    //Deserialize the queryResult
    JsonNode jsonObject = om.readTree(queryResult);
    String queryId = jsonObject.get("queryId").textValue();


    PollRequest pollQuery = new PollRequest(queryId);

    JsonNode jsonObjectPollQuery = om.valueToTree(pollQuery);


    do {

      url = new URL("http://" + ip + "/jsonservlet/poll");

      String pollResult = sendJsonRequest(charset, url, jsonObjectPollQuery);


      if (pollResult != null) {
        JsonNode j = om.readTree(pollResult);
        fileName = j.get("fileName").textValue();
      }

      //Poll after 5 seconds
      Thread.sleep(Constant.pollingTime);

    } while (fileName.equals(Constant.RETURN_PROCESSING_FILE_NAME));

    logger.info("FileName: " + fileName);

    return fileName;
  }

  public String doPostConnection(CCConnection conn, String cookie, String referer, String charset) throws Exception {

    String connectionResult = null;

    ConnectionRequest connection = new ConnectionRequest(conn.getDatabase(), conn.getTenantMppdbId(), conn.getInfo().getProperty("user"), conn.getInfo().getProperty("password"));

    JsonNode jsonObjectConnection = om.valueToTree(connection);

    URL url = new URL("http://" + conn.getIp() + "/jsonservlet/connection");
    connectionResult = sendJsonRequest(charset, url, jsonObjectConnection);

    return connectionResult;
  }

  public void doPostDisconnection(CCConnection conn, String cookie, String referer, String charset) throws Exception {

    ConnectionRequest connection = new ConnectionRequest(conn.getConnectionId());

    JsonNode jsonObjectDisconnection = om.valueToTree(connection);

    URL url = new URL("http://" + conn.getIp() + "/jsonservlet/disconnection");
    sendJsonRequest(charset, url, jsonObjectDisconnection);

  }

  public String sendJsonRequest(String charset, URL url, JsonNode jsonObjectQuery) throws Exception {

    String queryResult = null;

    try {

      byte[] jsonObjectBytes = om.writeValueAsBytes(jsonObjectQuery);
      HttpURLConnection URLConn = (HttpURLConnection) url.openConnection();

      URLConn.setDoOutput(true);
      URLConn.setDoInput(true);
      ((HttpURLConnection) URLConn).setRequestMethod("POST");
      URLConn.setUseCaches(false);
      URLConn.setAllowUserInteraction(true);
      HttpURLConnection.setFollowRedirects(true);
      URLConn.setInstanceFollowRedirects(true);

      URLConn.setRequestProperty("Content-Type", "application/json");
      URLConn.setRequestProperty("Content-Length", String.valueOf(jsonObjectBytes.length));

      URLConn.connect();

      java.io.DataOutputStream dos = new java.io.DataOutputStream(URLConn.getOutputStream());
      dos.write(jsonObjectBytes);
      dos.close();

      if(URLConn.getResponseCode() != 200)
        throw genSQLException(URLConn.getErrorStream());

      java.io.BufferedReader rd = new java.io.BufferedReader(new java.io.InputStreamReader(URLConn.getInputStream(), charset));

      String response;

      while ((response = rd.readLine()) != null) {
        queryResult = response;
      }
      rd.close();
      URLConn.disconnect();

    } catch (Exception e) {
      throw e;
    }

    return queryResult;
  }

  public SQLException genSQLException(InputStream stream) throws Exception {
    ObjectMapper om = JacksonHelper.getObjectMapper();
    HTTPJsonResponse response = om.readValue(stream, HTTPJsonResponse.class);
    String full_error = response.getError_message();

    return VerticaSQLRegexUtil.getSQLExceptionFromMessage(full_error);
  }

}

