package com.levkopo.vs.component.object;

import com.levkopo.vs.expression.Expression;

public class Attribute {
	private final Expression defaultExpression;
	private final String name;

	public Attribute(String name, Expression defaultExpression) {
		this.defaultExpression = defaultExpression;
		this.name = name;
	}

	public Expression getDefaultExpression() {
		return defaultExpression;
	}

	public String getName() {
		return name;
	}

	public boolean hasDefaultExpression() {
		return getDefaultExpression() != null;
	}

	protected Object[] stringValues() {
		return new Object[] { getName(), getDefaultExpression() };
	}
}