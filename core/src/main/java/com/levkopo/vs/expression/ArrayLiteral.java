package com.levkopo.vs.expression;

import com.github.bloodshura.ignitium.collection.view.XArrayView;
import com.github.bloodshura.ignitium.collection.view.XView;
import com.github.bloodshura.ignitium.util.XApi;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.value.ArrayValue;
import com.levkopo.vs.value.Value;

public class ArrayLiteral implements Expression {
	private final Expression[] expressions;

	public ArrayLiteral(Expression... expressions) {
		XApi.requireNonNull(expressions, "expressions");

		this.expressions = expressions;
	}

	public XView<Expression> getExpressions() {
		return new XArrayView<>(expressions);
	}

	@Override
	public Value resolve(Context context, Context vars_context) throws ScriptRuntimeException {
		ArrayValue value = new ArrayValue(getExpressions().size());
		int i = 0;

		for (Expression expression : getExpressions()) {
			value.set(vars_context, i++, expression.resolve(context, vars_context));
		}

		return value;
	}

	@Override
	public String toString() {
		return "arrdef(" + getExpressions() + ')';
	}
}