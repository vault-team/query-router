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

import vault.queryrouter.common.exception.NoMPPDBException;
import vault.queryrouter.models.MPPDB;
import vault.queryrouter.models.dao.MPPDBDAO;
import vault.queryrouter.models.dao.TenantMPPDBDAO;
import org.hibernate.StatelessSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MPPDBFinder {

  private static Logger logger = LoggerFactory.getLogger(MPPDBFinder.class);
  public static MPPDBFinder singleton = null;

  public static MPPDBFinder getInstance() {
    if (singleton == null) {
      singleton = new MPPDBFinder();
    }
    return singleton;
  }

  public MPPDB getAvblMppdb(StatelessSession session, int tenantMppdbId, List<String> excluded_mppdb_list) throws Exception {

    MPPDB ret_mppdb = null;
    int minCount = -1; //min connection count of a mppdb

    int tenantMppdbGroupId = TenantMPPDBDAO.getTenantMppdbGroupId(session, tenantMppdbId);

    List<MPPDB> mppdbs = MPPDBDAO.getMppdbs(session, tenantMppdbGroupId);

    if(mppdbs.size() == excluded_mppdb_list.size()){
      throw new NoMPPDBException("No mppdb can be used....");
    }

    for (MPPDB mppdb : mppdbs) {
      if(!excluded_mppdb_list.contains(mppdb.getMppdbIp())) {


        int mppdb_usage_count = ConnectionManager.getInstance().getNumByMPPDBId(session, mppdb.getMppdbId());
        //If no one use this MPPDB, then use it !
        if (mppdb_usage_count == 0) {
          return mppdb;
        } else {
          if (minCount == -1 || minCount > mppdb_usage_count) {
            minCount = mppdb_usage_count;
            ret_mppdb = mppdb;
          }
        }

      }
    }

    return ret_mppdb;
  }
}