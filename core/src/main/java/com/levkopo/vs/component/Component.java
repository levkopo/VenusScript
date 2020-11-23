package com.levkopo.vs.component;

import com.levkopo.vs.executor.ApplicationContext;

public abstract class Component {
	private Container parent;
	private int sourceLine;

	public ApplicationContext getApplicationContext() {
		return getParent().getApplicationContext();
	}

	public final Container getParent() {
		return parent;
	}

	public Script getScript() {
		return getParent().getScript();
	}

	public final int getSourceLine() {
		return sourceLine;
	}

	public final boolean hasParent() {
		return getParent() != null;
	}

	public void setParent(Container parent) {
		this.parent = parent;
	}

	public void setSourceLine(int sourceLine) {
		this.sourceLine = sourceLine;
	}

	@Override
	public abstract String toString();
}