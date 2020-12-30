package com.levkopo.vs.expression;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.value.Value;

public class Variable implements Expression {
	private final String name;

	public Variable(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public Value resolve(Context context, Context vars_context) throws ScriptRuntimeException {
		if(vars_context.hasVar(getName()))
			return vars_context.getVarValue(getName());

		return context.getVarValue(getName());
	}

	@Override
	public String toString() {
		return "var(" + getName() + ')';
	}
}