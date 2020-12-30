package com.levkopo.vs.expression;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.value.Value;

public class Attribution implements Expression {
	private final String name;
	private final Expression expression;

	public Attribution(String name, Expression expression) {
		this.name = name;
		this.expression = expression;
	}

	public String getName() {
		return name;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public Value resolve(Context context, Context vars_context) throws ScriptRuntimeException {
		Value value = getExpression().resolve(context, vars_context);
		vars_context.setVar(getName(), value);

		return value;
	}

	@Override
	public String toString() {
		return "attribution(" + getName() + "=" + getExpression() + ')';
	}
}