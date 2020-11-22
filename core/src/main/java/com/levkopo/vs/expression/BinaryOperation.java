package com.levkopo.vs.expression;

import com.github.bloodshura.ignitium.util.XApi;
import com.levkopo.vs.exception.runtime.IncompatibleTypesException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.operator.BinaryOperator;
import com.levkopo.vs.value.Value;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.util.Map;

import static com.sun.xml.internal.ws.org.objectweb.asm.Opcodes.*;
import static com.sun.xml.internal.ws.org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

public class BinaryOperation implements Expression {
	private final Expression left;
	private final BinaryOperator operator;
	private final Expression right;

	public BinaryOperation(BinaryOperator operator, Expression left, Expression right) {
		XApi.requireNonNull(left, "left");
		XApi.requireNonNull(operator, "operator");
		XApi.requireNonNull(right, "right");

		this.left = left;
		this.operator = operator;
		this.right = right;
	}

	public Expression getLeft() {
		return left;
	}

	public BinaryOperator getOperator() {
		return operator;
	}

	public Expression getRight() {
		return right;
	}

	@Override
	public Value resolve(Context context, Context vars_context) throws ScriptRuntimeException {
		Value left = getLeft().resolve(context, vars_context);
		Value right = getRight().resolve(context, vars_context);
		Value result = getOperator().operate(vars_context, left, right);

		if (result == null) {
			throw new IncompatibleTypesException(context, "Operator " + getOperator() + " cannot be applied with " + left.getType() + " and " + right.getType());
		}

		return result;
	}

	@Override
	public String toString() {
		return "bioperation([" + getLeft() + "] " + getOperator() + " [" + getRight() + "])";
	}
}