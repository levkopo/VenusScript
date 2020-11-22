package com.levkopo.vs.exception.runtime;

import com.github.bloodshura.ignitium.collection.view.XView;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.type.Type;

public class UndefinedFunctionException extends ScriptRuntimeException {
	public UndefinedFunctionException(Context context, String functionName, XView<Type> argumentTypes) {
		super(context, "No definition found for a function named \"" + functionName + "\" taking argument types: " + argumentTypes);
	}
}