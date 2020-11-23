package com.levkopo.vs.value;

import com.levkopo.vs.compiler.KeywordDefinitions;
import com.levkopo.vs.type.PrimitiveType;

public class VariableRefValue extends Value {
	private final String value;

	public VariableRefValue(String value) {
		super(PrimitiveType.VARIABLE_REFERENCE);
		this.value = value;
	}

	@Override
	public VariableRefValue clone() {
		return new VariableRefValue(value());
	}

	@Override
	public String toString() {
		return KeywordDefinitions.COLON + value();
	}

	@Override
	public String value() {
		return value;
	}
}