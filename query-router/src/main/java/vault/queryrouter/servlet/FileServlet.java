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

import vault.queryrouter.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileServlet extends HttpServlet {

  private static Logger logger = LoggerFactory.getLogger(FileServlet.class);

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

    /***************************************************
     * URL: /fileservlet/downloadfileservlet
     ****************************************************/
    if (request.getPathInfo().equals("/downloadfileservlet")) {
      // reads input file from an absolute path
      String fileName = request.getParameter("fileName");
      String filePath = Constant.DOWNLOAD_PATH + fileName;
      System.out.println(filePath);
      File downloadFile = new File(filePath);
      FileInputStream inStream = new FileInputStream(downloadFile);

      // if you want to use a relative path to context root:
      String relativePath = getServletContext().getRealPath("");
      logger.info("relativePath = " + relativePath);

      // obtains ServletContext
      ServletContext context = getServletContext();

      // gets MIME type of the file
      String mimeType = context.getMimeType(filePath);
      if (mimeType == null) {
        // set to binary type if MIME mapping not found
        mimeType = "application/octet-stream";
      }
      logger.info("MIME type: " + mimeType);

      // modifies response
      response.setContentType(mimeType);
      response.setContentLength((int) downloadFile.length());

      // forces download
      String headerKey = "Content-Disposition";
      String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
      response.setHeader(headerKey, headerValue);

      // obtains response's output stream
      OutputStream outStream = response.getOutputStream();

      byte[] buffer = new byte[4096];
      int bytesRead = -1;

      while ((bytesRead = inStream.read(buffer)) != -1) {
        outStream.write(buffer, 0, bytesRead);
      }

      inStream.close();
      outStream.close();

      //delete the csv file after transferring the data
      downloadFile.delete();




    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    /***************************************************
     * URL: /fileservlet/uploadfileservlet
     ****************************************************/
    //TODO when upload file, rename uploaded file
    if (request.getPathInfo().equals("/uploadfileservlet")) {
      final String SAVE_DIR = Constant.UPLOAD_PATH;
      final int BUFFER_SIZE = 4096;

      // Gets file name for HTTP header
      String fileName = request.getHeader("connectionId") + "_" + request.getHeader("fileName");
      File saveFile = new File(SAVE_DIR + fileName);

      // opens input stream of the request for reading data
      InputStream inputStream = request.getInputStream();

      // opens an output stream for writing file
      FileOutputStream outputStream = new FileOutputStream(saveFile);

      byte[] buffer = new byte[BUFFER_SIZE];
      int bytesRead = -1;

      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }

      outputStream.close();
      inputStream.close();

      logger.info("File written to: " + saveFile.getAbsolutePath());

      // sends response to client
      response.getWriter().print(fileName);



    }
  }

}