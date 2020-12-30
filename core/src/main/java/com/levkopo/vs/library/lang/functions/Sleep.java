package com.levkopo.vs.library.lang.functions;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.Value;

import java.util.concurrent.TimeUnit;

@MethodArgs(Value.class)
@MethodName("sleep")
public class Sleep extends VoidMethod {
	@Override
	public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		Number timeInMillis = (Number) descriptor.get(0).value();
		try {
			TimeUnit.MILLISECONDS.sleep(timeInMillis.longValue());
		} catch (InterruptedException ignored) {}
	}
}