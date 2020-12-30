package com.levkopo.vs.exception.runtime;

import com.levkopo.vs.executor.Context;

public class ScriptRuntimeException extends Exception {
	private final Context context;

	public ScriptRuntimeException(Context context, CharSequence message) {
		super(message + (context!=null?
				" at line " + context.getApplicationContext().currentLine() + " in \"" + context.getScript().getDisplayName() + "\""
				:" || ALTERNATIVE ERROR: CONTEXT IS NULL!"));
		this.context = context;
	}

	public ScriptRuntimeException(Context context, CharSequence message, Throwable cause) {
		super(message + " at line " + context.getApplicationContext().currentLine() + " in \"" + context.getScript().getDisplayName() + "\"", cause);
		this.context = context;
	}

	public ScriptRuntimeException(Context context, Throwable cause) {
		super("Runtime error at line " + context.getApplicationContext().currentLine() + " in \"" + context.getScript().getDisplayName() + "\"", cause);
		this.context = context;
	}

	public Context getContext() {
		return context;
	}
}