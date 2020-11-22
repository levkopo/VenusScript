package com.levkopo.vs.exception.runtime;

import com.levkopo.vs.executor.Context;

public class InvalidFunctionParameterException extends ScriptRuntimeException {
	public InvalidFunctionParameterException(Context context, CharSequence message) {
		super(context, message);
	}
}