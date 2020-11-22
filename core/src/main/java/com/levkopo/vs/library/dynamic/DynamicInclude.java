package com.levkopo.vs.library.dynamic;

import com.levkopo.vs.component.Script;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.StringValue;

@MethodArgs({ StringValue.class, BoolValue.class })
@MethodName("dynamicInclude")
public class DynamicInclude extends VoidMethod {
	@Override
	public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		StringValue includeName = (StringValue) descriptor.get(0);
		BoolValue maybe = (BoolValue) descriptor.get(1);
		Script script = context.getScript();

		try {
			script.include(includeName.value(), maybe.value());
		} catch (ScriptCompileException exception) {
			throw new ScriptRuntimeException(context, "Could not include script: " + exception.getMessage(), exception.getCause());
		}
	}
}
