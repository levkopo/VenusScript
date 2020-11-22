package com.levkopo.vs.component.branch;

import com.levkopo.vs.component.Container;
import com.levkopo.vs.expression.Expression;

public class WhileContainer extends Container implements Breakable {
	private final Expression condition;

	public WhileContainer(Expression condition) {
		this.condition = condition;
	}

	public Expression getCondition() {
		return condition;
	}

	@Override
	public String toString() {
		return "while(" + getCondition() + ')';
	}
}