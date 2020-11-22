package com.levkopo.vs.exception.runtime;

import com.levkopo.vs.executor.Context;

public class InvalidArrayAccessException extends ScriptRuntimeException {
	public InvalidArrayAccessException(Context context, CharSequence message) {
		super(context, message);
	}
}