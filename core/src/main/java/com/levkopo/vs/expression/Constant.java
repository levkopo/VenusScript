package com.levkopo.vs.expression;

import com.levkopo.vs.executor.Context;
import com.levkopo.vs.value.Value;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.util.Map;

public class Constant implements Expression {
	private final Value value;

	public Constant(Value value) {
		this.value = value;
	}

	public Value getValue() {
		return value;
	}

	@Override
	public Value resolve(Context context, Context vars_context) {
		return value;
	}

	@Override
	public String toString() {
		return "const(" + getValue() + ')';
	}

	@Override
	public MethodVisitor visit(MethodVisitor visitor, Map<String, Integer> variables) {
		visitor.visitLdcInsn(getValue().value());
		return visitor;
	}
}