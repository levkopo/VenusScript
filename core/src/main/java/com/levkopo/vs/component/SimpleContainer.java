package com.levkopo.vs.component;

public class SimpleContainer extends Container {
	private final String name;

	public SimpleContainer() {
		this("container");
	}

	public SimpleContainer(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + "()";
	}
}