package com.levkopo.vs.library.std;

import com.github.bloodshura.ignitium.sys.XSystem;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.DecimalValue;
import com.levkopo.vs.value.IntegerValue;

@MethodArgs({ DecimalValue.class, IntegerValue.class })
@MethodName("beep")
public class Beep extends VoidMethod {
	@Override
	public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		DecimalValue frequency = (DecimalValue) descriptor.get(0);
		IntegerValue duration = (IntegerValue) descriptor.get(1);

		XSystem.getDesktop().beep(frequency.value(), duration.value());
	}
}