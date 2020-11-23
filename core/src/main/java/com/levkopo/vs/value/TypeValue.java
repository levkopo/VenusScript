package com.levkopo.vs.value;

import com.levkopo.vs.type.PrimitiveType;
import com.levkopo.vs.type.Type;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public class TypeValue extends Value {
	private final Type value;

	public TypeValue(Type value) {
		super(PrimitiveType.TYPE);
		this.value = value;
	}

	@Override
	public TypeValue clone() {
		return new TypeValue(value());
	}

	@Override
	public String toString() {
		return value().toString();
	}

	@Override
	public MethodVisitor visit(MethodVisitor visitor) {
		visitor.visitLdcInsn(toString());
		return visitor;
	}

	@Override
	public Type value() {
		return value;
	}
}