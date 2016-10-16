//////////////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2016, João Vitor Verona Biazibetti - All Rights Reserved                /
//                                                                                       /
// This program is free software: you can redistribute it and/or modify                  /
// it under the terms of the GNU General Public License as published by                  /
// the Free Software Foundation, either version 3 of the License, or                     /
// (at your option) any later version.                                                   /
//                                                                                       /
// This program is distributed in the hope that it will be useful,                       /
// but WITHOUT ANY WARRANTY; without even the implied warranty of                        /
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the                          /
// GNU General Public License for more details.                                          /
//                                                                                       /
// You should have received a copy of the GNU General Public License                     /
// along with this program. If not, see <http://www.gnu.org/licenses/>.                  /
//                                                                                       /
// https://www.github.com/BloodShura                                                     /
//////////////////////////////////////////////////////////////////////////////////////////

package com.github.bloodshura.venus.operator;

import com.github.bloodshura.venus.executor.Context;
import com.github.bloodshura.venus.value.Value;
import com.github.bloodshura.x.collection.view.XArrayView;
import com.github.bloodshura.x.collection.view.XView;
import com.github.bloodshura.x.util.layer.XApi;

import java.util.function.BiFunction;

/**
 * BinaryOperator.java
 *
 * @author <a href="https://www.github.com/BloodShura">BloodShura</a> (João Vitor Verona Biazibetti)
 * @contact joaaoverona@gmail.com
 * @date 06/05/16 - 01:51
 * @since GAMMA - 0x3
 */
public class BinaryOperator implements Operator {
  private final BiFunction<Value, Value, Value> function;
  private final XView<String> identifiers;
  private final String name;

  public BinaryOperator(String name, BiFunction<Value, Value, Value> function, String... identifiers) {
    XApi.requireNonNull(function, "function");
    XApi.requireNonNull(identifiers, "identifiers");
    XApi.requireNonNull(name, "name");

    this.function = function;
    this.identifiers = new XArrayView<>(identifiers);
    this.name = name;
  }

  public BiFunction<Value, Value, Value> getFunction() {
    return function;
  }

  @Override
  public XView<String> getIdentifiers() {
    return identifiers;
  }

  public final Value operate(Context context, Value left, Value right) {
    return getFunction().apply(left, right);
  }

  @Override
  public String toString() {
    return name;
  }
}