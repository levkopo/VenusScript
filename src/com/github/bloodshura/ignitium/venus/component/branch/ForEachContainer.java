/*
 * Copyright (c) 2013-2018, João Vitor Verona Biazibetti - All Rights Reserved
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * https://www.github.com/BloodShura
 */

package com.github.bloodshura.ignitium.venus.component.branch;

import com.github.bloodshura.ignitium.venus.component.Container;
import com.github.bloodshura.ignitium.venus.expression.Expression;

public class ForEachContainer extends Container implements Breakable {
	private final Expression iterable;
	private final String varName;

	public ForEachContainer(String varName, Expression iterable) {
		this.iterable = iterable;
		this.varName = varName;
	}

	public Expression getIterable() {
		return iterable;
	}

	public String getVarName() {
		return varName;
	}

	@Override
	public String toString() {
		return "foreach(" + getVarName() + " in " + getIterable() + ')';
	}
}