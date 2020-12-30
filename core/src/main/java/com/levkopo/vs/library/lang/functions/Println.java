package com.levkopo.vs.library.lang.functions;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.executor.OutputReference;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.function.annotation.MethodVarArgs;
import com.levkopo.vs.value.Value;

@MethodName("println")
@MethodVarArgs
public class Println extends VoidMethod {
	@Override
	public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		OutputReference reference = context.getApplicationContext().getUserData("out", OutputReference.class);

		if (reference != null) {
			if (!descriptor.isEmpty()) {
				for (Value argument : descriptor.getValues()) {
					reference.output(argument.toString() + '\n');
				}
			} else {
				reference.output("\n");
			}
		}
	}
}
