package com.levkopo.vs.exception.runtime;

import com.levkopo.vs.executor.Context;
import com.levkopo.vs.type.Type;

import java.util.List;

public class UndefinedFunctionException extends ScriptRuntimeException {
	public UndefinedFunctionException(Context context, String functionName, List<Type> argumentTypes) {
		super(context, "No definition found for a function named \"" + functionName + "\" taking argument types: " + argumentTypes);
	}
}