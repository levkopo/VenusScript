package com.github.bloodshura.ignitium.venus.exception.runtime;

import com.github.bloodshura.ignitium.venus.executor.Context;

public class InvalidMapAccessException extends ScriptRuntimeException {
	public InvalidMapAccessException(Context context, CharSequence message) {
		super(context, message);
	}
}
