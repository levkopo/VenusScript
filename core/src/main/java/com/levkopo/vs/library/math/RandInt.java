package com.levkopo.vs.library.math;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.Value;

@MethodArgs({ Value.class, Value.class })
@MethodName("randInt")
public class RandInt extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		long minValue = ((Number) descriptor.get(0).value()).longValue();
		long maxValue = ((Number) descriptor.get(1).value()).longValue();

		if (minValue > maxValue) {
			long temp = maxValue;

			maxValue = minValue;
			minValue = temp;
		}

		return new IntegerValue(minValue + (long) (Math.random() * (maxValue - minValue)));
	}
}