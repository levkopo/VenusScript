package com.levkopo.vs.value;

import com.levkopo.vs.compiler.KeywordDefinitions;
import com.levkopo.vs.type.PrimitiveType;

public class FunctionRefValue extends Value {
	private final String value;

	public FunctionRefValue(String value) {
		super(PrimitiveType.FUNCTION_REFERENCE);
		this.value = value;
	}

	@Override
	public FunctionRefValue clone() {
		return new FunctionRefValue(value());
	}

	@Override
	public String toString() {
		return KeywordDefinitions.FUNCTION_REFERENCE + value();
	}

	@Override
	public String value() {
		return value;
	}
}