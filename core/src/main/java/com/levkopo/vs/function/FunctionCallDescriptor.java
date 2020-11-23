package com.levkopo.vs.function;

import com.levkopo.vs.expression.Expression;
import com.levkopo.vs.expression.FunctionCall;
import com.levkopo.vs.value.Value;

import java.util.List;
import java.util.function.Function;

public class FunctionCallDescriptor {
	private final FunctionCall caller;
	private final List<Expression> expressions;
	private final List<Value> values;

	public FunctionCallDescriptor(FunctionCall caller, List<Expression> expressions, List<Value> values) {
		this.caller = caller;
		this.expressions = expressions;
		this.values = values;
	}

	public int count() {
		return getValues().size();
	}

	public Value get(int index) {
		return getValues().get(index);
	}

	public FunctionCall getCaller() {
		return caller;
	}

	public Value getOr(int index, Value value) {
		return index >= 0 && index < getValues().size() ? getValues().get(index) : value;
	}

	public List<Expression> getExpressions() {
		return expressions;
	}

	public List<Value> getValues() {
		return values;
	}

	public boolean isEmpty() {
		return count() == 0;
	}

	public <E> E transform(int index, Function<Value, E> function, E or) {
		if (index >= 0 && index < getValues().size()) {
			Value value = get(index);

			return function.apply(value);
		}

		return or;
	}
}