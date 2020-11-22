package com.levkopo.vs.library.std;

import com.levkopo.vs.exception.runtime.AssertionException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.type.PrimitiveType;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.Value;

@MethodArgs(Value.class)
@MethodName("assert")
public class Assert extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		Value value = descriptor.get(0);

		if (value instanceof BoolValue) {
			BoolValue boolValue = (BoolValue) value;

			if (boolValue.value()) {
				return null;
			}

			throw new AssertionException(context, "Assertion failed");
		}

		throw new AssertionException(context, "Assertion expected a value of type " + PrimitiveType.BOOLEAN + "; received " + value.getType());
	}
}