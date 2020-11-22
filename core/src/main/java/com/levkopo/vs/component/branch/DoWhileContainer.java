package com.levkopo.vs.component.branch;

import com.levkopo.vs.component.Container;
import com.levkopo.vs.expression.Expression;

public class DoWhileContainer extends Container implements Breakable {
	private Expression condition;

	public DoWhileContainer(Expression condition) {
		this.condition = condition;
	}

	public Expression getCondition() {
		return condition;
	}

	public boolean hasCondition() {
		return getCondition() != null;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "dowhile(" + getCondition() + ')';
	}
}