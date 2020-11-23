package com.levkopo.vs.function;

import com.levkopo.vs.type.Type;

public class Argument {
	private final String name;
	private final Type type;

	public Argument(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}
}