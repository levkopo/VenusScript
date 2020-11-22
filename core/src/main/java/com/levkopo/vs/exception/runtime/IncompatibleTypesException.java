package com.levkopo.vs.exception.runtime;

import com.levkopo.vs.executor.Context;

public class IncompatibleTypesException extends ScriptRuntimeException {
	public IncompatibleTypesException(Context context, CharSequence message) {
		super(context, message);
	}
}