package com.levkopo.vs.function;

import com.github.bloodshura.ignitium.object.Base;
import com.levkopo.vs.type.Type;

import javax.annotation.Nonnull;

public class Argument extends Base {
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

	@Nonnull
	@Override
	protected Object[] stringValues() {
		return new Object[] { getName(), getType() };
	}
}