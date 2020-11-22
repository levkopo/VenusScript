package com.levkopo.vs.value;

import com.levkopo.vs.type.Type;

public abstract class IterableValue extends Value implements Iterable<Value> {
	public IterableValue(Type type) {
		super(type);
	}
}