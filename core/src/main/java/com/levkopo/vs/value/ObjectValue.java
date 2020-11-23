package com.levkopo.vs.value;

import com.levkopo.vs.component.object.ObjectDefinition;
import com.levkopo.vs.executor.Context;

public class ObjectValue extends Value {
	private final Context context;
	private final ObjectDefinition definition;

	public ObjectValue(ObjectDefinition definition, Context context) {
		super(definition.getType());
		this.context = context;
		this.definition = definition;
	}

	@Override
	public ObjectValue clone() {
		try {
			return new ObjectValue(getDefinition(), getContext().clone());
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	public Context getContext() {
		return context;
	}

	public ObjectDefinition getDefinition() {
		return definition;
	}

	@Override
	public Object value() {
		return this;
	}

	@Override
	public String toString() {
		return getDefinition().getName() + "(" + getDefinition().getAttributes() + ')';
	}
}