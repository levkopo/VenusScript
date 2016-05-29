//////////////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2016, João Vitor Verona Biazibetti - All Rights Reserved                /
//                                                                                       /
// Licensed under the GNU General Public License v3;                                     /
// you may not use this file except in compliance with the License.                      /
//                                                                                       /
// You may obtain a copy of the License at                                               /
//     http://www.gnu.org/licenses/gpl.html                                              /
//                                                                                       /
// Unless required by applicable law or agreed to in writing, software                   /
// distributed under the License is distributed on an "AS IS" BASIS,                     /
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.              /
// See the License for the specific language governing permissions and                   /
// limitations under the License.                                                        /
//                                                                                       /
// Written by João Vitor Verona Biazibetti <joaaoverona@gmail.com>, March 2016           /
// https://www.github.com/BloodShura                                                     /
//////////////////////////////////////////////////////////////////////////////////////////

package br.shura.venus.value;

import br.shura.venus.compiler.KeywordDefinitions;
import br.shura.venus.type.PrimitiveType;
import br.shura.x.util.layer.XApi;

/**
 * VariableRefValue.java
 *
 * @author <a href="https://www.github.com/BloodShura">BloodShura</a> (João Vitor Verona Biazibetti)
 * @contact joaaoverona@gmail.com
 * @date 16/05/16 - 00:29
 * @since GAMMA - 0x3
 */
public class VariableRefValue extends Value {
  private final String value;

  public VariableRefValue(String value) {
    super(PrimitiveType.VARIABLE_REFERENCE);
    XApi.requireNonNull(value, "value");

    this.value = value;
  }

  @Override
  public VariableRefValue clone() {
    return new VariableRefValue(value());
  }

  @Override
  public String toString() {
    return KeywordDefinitions.VARIABLE_REFERENCE + value();
  }

  @Override
  public String value() {
    return value;
  }
}