package com.levkopo.vs.component;

import com.levkopo.vs.expression.Expression;

public class SimpleComponent extends Component {
	private final Expression expression;

	public SimpleComponent(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public String toString() {
		return getExpression().toString();
	}
}