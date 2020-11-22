package com.levkopo.vs.function;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.value.Value;

public abstract class VoidMethod extends Method {
	@Override
	public final Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		callVoid(context, descriptor);

		return null;
	}

	public abstract void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException;
}