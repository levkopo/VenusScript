package com.levkopo.vs.expression;

import com.github.bloodshura.ignitium.util.XApi;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.value.Value;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.util.Map;

import static com.sun.xml.internal.ws.org.objectweb.asm.Opcodes.ALOAD;
import static com.sun.xml.internal.ws.org.objectweb.asm.Opcodes.LLOAD;

public class Variable implements Expression {
	private final String name;

	public Variable(String name) {
		XApi.requireNonNull(name, "name");

		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public Value resolve(Context context, Context vars_context) throws ScriptRuntimeException {
		if(context.hasVar(getName()))
			return context.getVarValue(getName());

		return vars_context.getVarValue(getName());
	}

	@Override
	public String toString() {
		return "var(" + getName() + ')';
	}

	@Override
	public MethodVisitor visit(MethodVisitor visitor, Map<String, Integer> variables) {
		if(variables.containsKey("A_"+getName()))
			visitor.visitVarInsn(ALOAD, variables.get("A_"+getName()));
		else if(variables.containsKey("L_"+getName()))
			visitor.visitVarInsn(LLOAD, variables.get("L_"+getName()));

		return visitor;
	}
}