package com.levkopo.vs.expression;

import com.levkopo.vs.exception.runtime.IncompatibleTypesException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.operator.UnaryOperator;
import com.levkopo.vs.value.Value;

public class UnaryOperation implements Expression {
	private final UnaryOperator operator;
	private final Expression expression;

	public UnaryOperation(UnaryOperator operator, Expression expression) {
		this.operator = operator;
		this.expression = expression;
	}

	public UnaryOperator getOperator() {
		return operator;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public Value resolve(Context context, Context vars_context) throws ScriptRuntimeException {
		Value value = getExpression().resolve(context, vars_context);
		Value result = getOperator().operate(vars_context, value);

		if (result == null) {
			throw new IncompatibleTypesException(context, "Operator " + getOperator() + " cannot be applied with " + value.getType());
		}

		return result;
	}

	@Override
	public String toString() {
		return "unioperation(" + getOperator() + " [" + getExpression() + "])";
	}
}