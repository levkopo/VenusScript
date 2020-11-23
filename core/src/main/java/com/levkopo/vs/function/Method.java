package com.levkopo.vs.function;

import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.function.annotation.MethodVarArgs;
import com.levkopo.vs.type.PrimitiveType;
import com.levkopo.vs.type.Type;
import com.levkopo.vs.value.Value;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class Method implements Function {
	private final List<Type> arguments;
	private final String name;
	private final boolean varArgs;

	public Method() {
		boolean hasMethodArgs = getClass().isAnnotationPresent(MethodArgs.class);
		boolean isMethodVarArgs = getClass().isAnnotationPresent(MethodVarArgs.class);

		this.arguments = new ArrayList<>();
		if (hasMethodArgs) {
			Class<? extends Value>[] args = getClass().getAnnotation(MethodArgs.class).value();

			for(Class<? extends Value> argument: args){
				arguments.add(PrimitiveType.forType(argument));
			}
		}

		this.name = getClass().getAnnotation(MethodName.class).value();
		this.varArgs = isMethodVarArgs;
	}

	@Override
	public final List<Type> getArgumentTypes() {
		return arguments;
	}

	@NotNull
	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final boolean isVarArgs() {
		return varArgs;
	}

	@Override
	public final String toString() {
		return "method(" + getName() + ')';
	}
}
