package com.levkopo.vs.expression;

import com.levkopo.vs.executor.Context;
import com.levkopo.vs.value.Value;

public class Constant implements Expression {
	private final Value value;

	public Constant(Value value) {
		this.value = value;
	}

	public Value getValue() {
		return value;
	}

	@Override
	public Value resolve(Context context, Context vars_context) {
		return value;
	}

	@Override
	public String toString() {
		return "const(" + getValue() + ')';
	}
}