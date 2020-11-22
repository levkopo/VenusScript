package com.levkopo.vs.exception.runtime;

import com.levkopo.vs.executor.Context;

public class InvalidMapAccessException extends ScriptRuntimeException {
	public InvalidMapAccessException(Context context, CharSequence message) {
		super(context, message);
	}
}
