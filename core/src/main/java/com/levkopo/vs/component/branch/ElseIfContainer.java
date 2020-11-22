package com.levkopo.vs.component.branch;

import com.levkopo.vs.expression.Expression;

public class ElseIfContainer extends IfContainer {
	public ElseIfContainer(Expression condition) {
		super(condition);
	}

	@Override
	public String toString() {
		return "elseif(" + getCondition() + ')';
	}
}