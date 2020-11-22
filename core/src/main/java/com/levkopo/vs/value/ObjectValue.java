package com.levkopo.vs.value;

import com.github.bloodshura.ignitium.charset.TextBuilder;
import com.github.bloodshura.ignitium.collection.tuple.Pair;
import com.levkopo.vs.component.object.ObjectDefinition;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.executor.VariableStructure;

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
		return new ObjectValue(getDefinition(), getContext().clone());
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
		TextBuilder attributes = new TextBuilder().setSeparator(", ");

		for (Pair<String, VariableStructure> pair : getContext().getVariables()) {
			attributes.append(pair.getLeft() + ": " + pair.getRight() + " [" + pair.getRight().getValue().getType() + ']');
		}

		return getDefinition().getName() + "(" + attributes + ')';
	}
}