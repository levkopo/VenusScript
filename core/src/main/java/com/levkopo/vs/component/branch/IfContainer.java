package com.levkopo.vs.component.branch;

import com.levkopo.vs.component.Container;
import com.levkopo.vs.expression.Expression;

public class IfContainer extends Container {
	private final Expression condition;

	public IfContainer(Expression condition) {
		this.condition = condition;
	}

	public Expression getCondition() {
		return condition;
	}

	@Override
	public String toString() {
		return "if(" + getCondition() + ')';
	}
}