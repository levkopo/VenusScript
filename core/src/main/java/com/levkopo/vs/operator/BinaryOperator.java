package com.levkopo.vs.operator;

import com.levkopo.vs.executor.Context;
import com.levkopo.vs.value.Value;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class BinaryOperator implements Operator {
	private final BiFunction<Value, Value, Value> function;
	private final List<String> identifiers;
	private final String name;

	public BinaryOperator(String name, BiFunction<Value, Value, Value> function, String... identifiers) {
		this.function = function;
		this.identifiers = Arrays.asList(identifiers);
		this.name = name;
	}

	public BiFunction<Value, Value, Value> getFunction() {
		return function;
	}

	@Override
	public List<String> getIdentifiers() {
		return identifiers;
	}

	public final Value operate(Context context, Value left, Value right) {
		return getFunction().apply(left, right);
	}

	@Override
	public String toString() {
		return name;
	}
}