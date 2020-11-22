package com.levkopo.vs.value;

import com.levkopo.vs.type.Type;

public abstract class NumericValue extends Value {
	public NumericValue(Type type) {
		super(type);
	}

	@Override
	public abstract Number value();
}