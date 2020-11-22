package com.levkopo.vs.component.branch;

import com.levkopo.vs.component.Container;
import com.levkopo.vs.expression.Expression;

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