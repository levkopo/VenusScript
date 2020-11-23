package com.levkopo.vs.library.std;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodName;

@MethodName("exit")
public class Exit extends VoidMethod {
	@Override
	public void callVoid(Context context, FunctionCallDescriptor descriptor) {
		System.exit(0);
	}
}