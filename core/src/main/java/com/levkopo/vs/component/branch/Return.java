package com.levkopo.vs.component.branch;

import com.levkopo.vs.component.Component;
import com.levkopo.vs.expression.Expression;

public class Return extends Component {
	private final Expression expression;

	public Return(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public String toString() {
		return "return(" + getExpression() + ')';
	}
}