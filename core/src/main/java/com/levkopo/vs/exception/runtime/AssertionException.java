package com.levkopo.vs.exception.runtime;

import com.levkopo.vs.executor.Context;

public class AssertionException extends ScriptRuntimeException {
	public AssertionException(Context context, CharSequence message) {
		super(context, message);
	}
}