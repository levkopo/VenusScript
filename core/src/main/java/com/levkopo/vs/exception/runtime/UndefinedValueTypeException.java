package com.levkopo.vs.exception.runtime;

import com.levkopo.vs.executor.Context;

public class UndefinedValueTypeException extends ScriptRuntimeException {
	public UndefinedValueTypeException(Context context, String name) {
		super(context, "No definition found for a type named \"" + name + "\"");
	}
}