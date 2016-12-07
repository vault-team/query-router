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

package vault.queryrouter.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import vault.common.models.ConnectionRequest;
import vault.common.models.HTTPJsonResponse;
import vault.common.models.PollRequest;
import vault.common.models.QueryRequest;
import vault.common.util.VerticaSQLRegexUtil;
import vault.queryrouter.common.Constant;
import vault.queryrouter.common.QueryLogStatus;
import vault.queryrouter.common.util.HibernateUtil;
import vault.common.util.JacksonHelper;
import vault.queryrouter.query.ConnectionManager;
import vault.queryrouter.query.MPPDBFinder;
import vault.queryrouter.query.QueryManager;
import org.hibernate.StatelessSession;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class TenantServiceAPI extends HttpServlet {
  //private static final long serialVersionUID = 1L;
  private static Logger logger = LoggerFactory.getLogger(MPPDBFinder.class);
  QueryManager manager = QueryManager.getInstance();;
  ObjectMapper mapper = JacksonHelper.getObjectMapper();
  SessionFactory factory = HibernateUtil.getSessionFactory();

  /***************************************************
   * URL: /jsonservlet
   * doPost(): receives JSON data, parse it, map it and send back as JSON
   ****************************************************/
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    /***************************************************
     * URL: /jsonservlet/query
     ****************************************************/
    if (request.getPathInfo().equals("/query")) {

      UUID queryId;
      QueryRequest queryRequest;
      StatelessSession session = factory.openStatelessSession();
      try {
        //Convert received JSON to QueryRequest Object
        queryRequest = mapper.readValue(request.getInputStream(), QueryRequest.class);

        logger.info("connection id: " + queryRequest.getConnectionId());

        //Obtain queryId for polling the query result
        queryId = manager.processQuery(session, queryRequest.getSql(), queryRequest.getMppdbId(), queryRequest.getUserId(), queryRequest.getTenantMppdbId(), queryRequest.getFileName(), queryRequest.getCommandType(), queryRequest.getConnectionId());

        queryRequest.setQueryId(queryId.toString());

        //Set response type to JSON
        response.setContentType("application/json");

        //Send List<QueryRequest> as JSON to client
        mapper.writeValue(response.getOutputStream(), queryRequest);

      } catch (Exception e) {
        logger.error("Failed to process", e);
        response.setStatus(500);
      }finally {
        session.close();
      }

    }
    /***************************************************
     * URL: /jsonservlet/poll
     ****************************************************/
    else if (request.getPathInfo().equals("/poll")) {
      String fileName;
      PollRequest pollRequest = null;

      StatelessSession session = factory.openStatelessSession();
      response.setContentType("application/json");
      try {
        pollRequest = mapper.readValue(request.getInputStream(), PollRequest.class);

        //Execution is finished
        if (manager.checkFinishExecution(session, pollRequest.getQueryId()).equals(QueryLogStatus.Success.toString())) {
          fileName = manager.getResult(pollRequest.getQueryId());
          pollRequest.setFileName(fileName);
        }
        // Execution is still processing
        else {
          pollRequest.setFileName(Constant.RETURN_PROCESSING_FILE_NAME);
        }

        //Set response type to JSON
        response.setContentType("application/json");

        //Send List<QueryRequest> as JSON to client
        mapper.writeValue(response.getOutputStream(), pollRequest);
      } catch (Exception e) {
        logger.error("Failed to process", e);
        exceptionHandle(e, response);
      }finally {
        session.close();
      }

    }
    /***************************************************
     * URL: /jsonservlet/connection
     ****************************************************/
    else if (request.getPathInfo().equals("/connection")) {

      ConnectionRequest connectionRequest = null;
      StatelessSession session = factory.openStatelessSession();

      try {

        connectionRequest = mapper.readValue(request.getInputStream(), ConnectionRequest.class);

        String connection_id = ConnectionManager.getInstance().createConnection(session, connectionRequest);
        logger.debug(connection_id);
        connectionRequest.setConnectionId(connection_id);

        response.setContentType("application/json");

        mapper.writeValue(response.getOutputStream(), connectionRequest);

      } catch (Exception e) {
        logger.error("Fail to connect DB connection",e);
        exceptionHandle(e, response);
      }
      finally {
        session.close();
      }

    }
    /***************************************************
     * URL: /jsonservlet/disconnection
     ****************************************************/
    else if (request.getPathInfo().equals("/disconnection")) {
      ConnectionRequest connectionRequest = null;
      connectionRequest = mapper.readValue(request.getInputStream(), ConnectionRequest.class);

      //close the connection object
      try {
        ConnectionManager.getInstance().closeConnection(connectionRequest.getConnectionId());
      } catch (Exception e) {
        logger.error("Fail to disconnect DB connection",e);
        exceptionHandle(e, response);
      }
    }
  }

  /**
   * Function to handle exception
   * Classify the exception is SQLException(Error by client) or Backend Exception
   * Return SQLException only to client
   * @param e
   * @param response
   */
  public void exceptionHandle(Exception e, HttpServletResponse response){

    response.setStatus(500);

    HTTPJsonResponse jsonResponse = new HTTPJsonResponse();
    jsonResponse.setStatus(HTTPJsonResponse.Status.error);

    if(e instanceof SQLException){
      jsonResponse.setError_message(e.getMessage());
    }else{
      logger.error("Backend server exception",e);
      jsonResponse.setError_message(VerticaSQLRegexUtil.getErrorMessage("V000002","Backend server exception"));
    }

    try {
      mapper.writeValue(response.getOutputStream(), jsonResponse);
    } catch (IOException e1) {
      logger.error("Fail to output the error response", e1);
    }
  }

}


