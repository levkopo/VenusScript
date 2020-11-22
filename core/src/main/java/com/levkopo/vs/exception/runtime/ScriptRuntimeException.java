package com.levkopo.vs.exception.runtime;

import com.github.bloodshura.ignitium.exception.CheckedException;
import com.levkopo.vs.executor.Context;

public class ScriptRuntimeException extends CheckedException {
	private final Context context;

	public ScriptRuntimeException(Context context, CharSequence message) {
		super(message + " at line " + context.getApplicationContext().currentLine() + " in \"" + context.getScript().getDisplayName() + "\"");
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