package com.levkopo.vs.component.object;

import com.levkopo.vs.component.Container;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.expression.Constant;
import com.levkopo.vs.expression.NewObject;
import com.levkopo.vs.type.ObjectType;
import com.levkopo.vs.type.Type;
import com.levkopo.vs.value.ObjectValue;

import java.util.ArrayList;
import java.util.List;

public class ObjectDefinition extends Container {
	private final List<Attribute> attributes;
	private final String name;
	private final Type type;

	public ObjectDefinition(String name) {
		this.attributes = new ArrayList<>();
		this.name = name;
		this.type = new ObjectType(name);
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
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
		return null;
	}
}