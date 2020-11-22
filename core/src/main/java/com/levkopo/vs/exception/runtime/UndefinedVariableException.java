package com.levkopo.vs.exception.runtime;

import com.levkopo.vs.executor.Context;

public class UndefinedVariableException extends ScriptRuntimeException {
	private final String variableName;

	public UndefinedVariableException(Context context, String variableName) {
		super(context, "Undefined variable \"" + variableName + "\"");
		this.variableName = variableName;
	}

	public String getVariableName() {
		return variableName;
	}
}