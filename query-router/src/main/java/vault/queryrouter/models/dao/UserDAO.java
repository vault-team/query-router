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

package vault.queryrouter.models.dao;

import vault.queryrouter.common.exception.InvalidTenantMPPDBUsernameAndPassword;
import vault.queryrouter.models.User;
import org.hibernate.Criteria;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;


public class UserDAO {

  public static Logger logger = LoggerFactory.getLogger(UserDAO.class);

  public static User getUser(StatelessSession session, int tenantMppdbId, String userName, String password) throws InvalidTenantMPPDBUsernameAndPassword {
    try {
      Criteria criteria = session.createCriteria(User.class);
      criteria.add(Expression.eq("userName", userName));
      criteria.add(Expression.eq("tenantMppdbId", tenantMppdbId));
      criteria.add(Expression.eq("password", password));

      List<User> users  = criteria.list();
      if(users.size() == 0){
        throw new InvalidTenantMPPDBUsernameAndPassword();
      }
      return users.get(0);
    }
    catch(Exception e){
      logger.error("Fail to get User id",e);
      throw e;
    }
  }

  public static Iterator getUserList(StatelessSession session, int tenantMppdbId){
    //Find out the mmpdb_id which belongs to the current tenant group
    Criteria criteria = session.createCriteria(User.class);
    criteria.add(Expression.eq("tenantMppdbId", tenantMppdbId));
    Iterator users = criteria.list().iterator();

    return users;
  }

  public static String getPassword(StatelessSession session, String userName, int tenantMppdbId){
    Iterator users;
    String password = null;
    try {
      Criteria criteria = session.createCriteria(User.class);
      criteria.add(Expression.eq("userName", userName));
      criteria.add(Expression.eq("tenantMppdbId", tenantMppdbId));
      users = criteria.list().iterator();
      while (users.hasNext()) {
        User user = (User) users.next();
        password = user.getPassword();
      }
    }
    catch(Exception e){
      logger.error("Fail to get password",e);
      throw e;
    }
    return password;
  }

}
