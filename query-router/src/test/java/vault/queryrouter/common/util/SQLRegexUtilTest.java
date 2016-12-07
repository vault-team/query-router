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

package vault.queryrouter.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Case fpr SQLRegexUtilTools
 */
public class SQLRegexUtilTest{
	@Test
	public void getLocalFilePathFromCOPYStatement() throws Exception {
    assertEquals("/Users/Julian/Desktop/consumer.csv", SQLRegexUtil.getLocalFilePathFromCOPYStatement("COPY Market From LOCAL '/Users/Julian/Desktop/consumer.csv' DELIMITER ','"));

    try {
      SQLRegexUtil.getLocalFilePathFromCOPYStatement("SELECT * FROM A");
      fail();
    }catch(IllegalStateException e){
    }
	}

	@Test
  public void isCreateSchemaStatement() throws Exception {
    assertTrue(SQLRegexUtil.isCreateSchemaStatement("CREATE SCHEMA a"));
    assertTrue(SQLRegexUtil.isCreateSchemaStatement("   CREATE     SCHEMA a"));
    assertFalse(SQLRegexUtil.isCreateSchemaStatement("   CREATE     TABLE a"));
    assertTrue(SQLRegexUtil.isCreateSchemaStatement("CREATE SCHEMA IF NOT EXISTS a.b"));
    assertTrue(SQLRegexUtil.isCreateSchemaStatement("CREATE SCHEMA IF NOT EXISTS a.b"));
  }

  @Test
  public void getSchemaFromSetSearchPath() throws Exception {
    assertEquals("tm7_a", SQLRegexUtil.getSchemaFromSetSearchPath(" SET SEARCH_PATH TO tm7_a"));
  }

  @Test
  public void getSchemaFromCreateSchemaStatement() throws Exception {
    assertEquals("a", SQLRegexUtil.getSchemaFromCreateSchemaStatement("   CREATE     SCHEMA a"));
    assertEquals("b", SQLRegexUtil.getSchemaFromCreateSchemaStatement("CREATE SCHEMA IF NOT EXISTS a.b"));
    assertEquals("a", SQLRegexUtil.getSchemaFromCreateSchemaStatement("CREATE SCHEMA IF NOT EXISTS a"));
  }

}