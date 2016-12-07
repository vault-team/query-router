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

package vault.queryrouter;

import vault.queryrouter.common.Constant;
import vault.queryrouter.servlet.listener.BootListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vault.queryrouter.servlet.FileServlet;
import vault.queryrouter.servlet.TenantServiceAPI;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.TimeZone;

/**
 * Embedded jetty
 */
public class Main {

  public static void main(String[] args){

    Main.pirntConfig();
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    Logger logger = LoggerFactory.getLogger(Main.class);
    // Create a basic jetty server object that will listen on port 8080.
    // Note that if you set this to port 0 then a randomly available port
    // will be assigned that you can either look in the logs for the port,
    // or programmatically obtain it for use in test cases.
    Server server = new Server(new InetSocketAddress(Constant.LISTEN_HOST, Constant.LISTEN_PORT));

    try {

      ServletContextHandler context = new ServletContextHandler(
               ServletContextHandler.SESSIONS);
      context.setContextPath("/");
      context.setResourceBase(System.getProperty("java.io.tmpdir"));
      server.setHandler(context);

      // Boot and shutdown handler
      context.addEventListener(new BootListener());

      // Add dump servlet
      context.addServlet(TenantServiceAPI.class, "/jsonservlet/*");
      // Add default servlet
      context.addServlet(FileServlet.class, "/fileservlet/*");

      // Start things up!
      server.start();
      server.setStopAtShutdown(true);
      server.join();
    }catch(Exception e){
      logger.error("Exception in jetty",e);
    }
  }

  public static void pirntConfig(){
    System.out.println("====================================");
    System.out.println("Vault Query Router configuration:");

    Field[] attributes =  Constant.ConfigKeys.class.getDeclaredFields();
    for (Field f : attributes){
      String key = null;
      try {
        key = (String) f.get(null);
        System.out.println("\t"+key+" : "+Constant.configs.getString(key));
      } catch (IllegalAccessException e) {
        System.err.print("Fail to retrieve configuration value");
        System.exit(1);
      }
    }
  }
}
