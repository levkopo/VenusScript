package com.levkopo.vs.exception.runtime;

import com.levkopo.vs.executor.Context;

public class InvalidValueTypeException extends ScriptRuntimeException {
	public InvalidValueTypeException(Context context, CharSequence message) {
		super(context, message);
	}
}