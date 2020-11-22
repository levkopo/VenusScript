package com.levkopo.vs.library.std;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.executor.VariableStructure;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.Value;
import com.levkopo.vs.value.VariableRefValue;

@MethodArgs(VariableRefValue.class)
@MethodName("produce")
public class Produce extends VoidMethod {
	@Override
	public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		VariableRefValue reference = (VariableRefValue) descriptor.get(0);
		VariableStructure variable = context.getVar(reference.value());
		Object monitor;

		synchronized ((monitor = variable.getLockMonitor())) {
			Value value = variable.getValue();

			context.setVar(reference.value(), value.plus(new IntegerValue(1)));
			monitor.notifyAll();
		}
	}
}