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

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SerializableResultSetSerializer extends Serializer<SerializableResultSet> {

  @Override
  public void write(Kryo kryo, Output output, SerializableResultSet object) {
    kryo.writeClassAndObject(output, object.getSerializableResultSetMetaData());
    for(SerializableRow row : object.getRows()){
      kryo.writeClassAndObject(output, row);
    }
  }

  public SerializableResultSet read(Kryo kryo, Input input, Class<SerializableResultSet> type) {
    SerializableResultSetMetaData serializableResultSetMetaData = (SerializableResultSetMetaData) kryo.readClassAndObject(input);
    List<SerializableRow> rows = new ArrayList<>();
    try {
      while(input.available() > 0){
        rows.add((SerializableRow) kryo.readClassAndObject(input));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return new SerializableResultSet(rows, serializableResultSetMetaData);
  }
}
