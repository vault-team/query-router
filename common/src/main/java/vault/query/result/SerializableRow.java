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

package vault.query.result;

import java.beans.Transient;

public class SerializableRow {

  private Object[] elements;

  public SerializableRow() {
  }

  public SerializableRow(Object[] elements) {
    this.elements = elements;
  }

  public Object[] getElements() {
    return elements;
  }

  public void setElements(Object[] elements) {
    this.elements = elements;
  }

  @Transient
  public Object get(int columnIndex){
    return elements[columnIndex-1];
  }
}
