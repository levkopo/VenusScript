package com.levkopo.vs.library.std;

import com.github.bloodshura.ignitium.threading.IgThread;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.VariableRefValue;

@MethodArgs(VariableRefValue.class)
@MethodName("wait")
public class WaitDefinition extends VoidMethod {
	@Override
	public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		VariableRefValue reference = (VariableRefValue) descriptor.get(0);

		while (!context.hasVar(reference.value())) {
			IgThread.stay(50L);
		}
	}
}