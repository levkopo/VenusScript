package com.levkopo.vs.component.object;

import com.github.bloodshura.ignitium.collection.list.XList;
import com.github.bloodshura.ignitium.collection.list.impl.XArrayList;
import com.levkopo.vs.component.Container;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.type.ObjectType;
import com.levkopo.vs.type.Type;

public class ObjectDefinition extends Container {
	private final XList<Attribute> attributes;
	private final String name;
	private final Type type;

	public ObjectDefinition(String name) {
		this.attributes = new XArrayList<>();
		this.name = name;
		this.type = new ObjectType(name);
	}

	public XList<Attribute> getAttributes() {
		return attributes;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	@Override
	public void setParent(Container parent) {
		super.setParent(parent);
		this.context = new Context(this, parent.getContext());
	}

	@Override
	public String toString() {
		return "objectdef(" + getName() + ')';
	}
}