package com.levkopo.vs.expression;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.value.ArrayValue;
import com.levkopo.vs.value.Value;

import java.util.Arrays;
import java.util.List;

public class ArrayLiteral implements Expression {
	private final Expression[] expressions;

	public ArrayLiteral(Expression... expressions) {
		this.expressions = expressions;
	}

	public List<Expression> getExpressions() {
		return Arrays.asList(expressions);
	}

	@Override
	public Value resolve(Context context, Context vars_context) throws ScriptRuntimeException {
		ArrayValue value = new ArrayValue();
		int i = 0;

		for (Expression expression : getExpressions()) {
			value.set(i++, expression.resolve(context, vars_context));
		}

		return value;
	}

	@Override
	public String toString() {
		return "arrdef(" + getExpressions() + ')';
	}
}