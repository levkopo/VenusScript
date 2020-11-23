package com.levkopo.vs.operator;

import com.levkopo.vs.executor.Context;
import com.levkopo.vs.value.Value;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class UnaryOperator implements Operator {
	private final Function<Value, Value> function;
	private final List<String> identifiers;
	private final String name;

	public UnaryOperator(String name, Function<Value, Value> function, String... identifiers) {
		this.function = function;
		this.identifiers = Arrays.asList(identifiers);
		this.name = name;
	}

	public Function<Value, Value> getFunction() {
		return function;
	}

	@Override
	public List<String> getIdentifiers() {
		return identifiers;
	}

	public final Value operate(Context context, Value value) {
		return getFunction().apply(value);
	}

	@Override
	public String toString() {
		return name;
	}
}