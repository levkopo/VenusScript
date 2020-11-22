package com.levkopo.vs.value;

import com.github.bloodshura.ignitium.util.XApi;
import com.levkopo.vs.compiler.KeywordDefinitions;
import com.levkopo.vs.type.PrimitiveType;

public class FunctionRefValue extends Value {
	private final String value;

	public FunctionRefValue(String value) {
		super(PrimitiveType.FUNCTION_REFERENCE);
		XApi.requireNonNull(value, "value");

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