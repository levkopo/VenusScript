package com.levkopo.vs.library.std;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.IntegerValue;

@MethodArgs(IntegerValue.class)
@MethodName("exit")
public class ExitWithCode extends VoidMethod {
	@Override
	public void callVoid(Context context, FunctionCallDescriptor descriptor) {
		IntegerValue code = (IntegerValue) descriptor.get(0);

		System.exit(code.value().intValue());
	}
}