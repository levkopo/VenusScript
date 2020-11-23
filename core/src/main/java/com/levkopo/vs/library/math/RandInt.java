package com.levkopo.vs.library.math;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.Value;

import java.util.Random;

@MethodArgs({ IntegerValue.class, IntegerValue.class })
@MethodName("randInt")
public class RandInt extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		IntegerValue min = (IntegerValue) descriptor.get(0);
		IntegerValue max = (IntegerValue) descriptor.get(1);
		long minValue = min.value();
		long maxValue = max.value();

		if (minValue > maxValue) {
			long temp = maxValue;

			maxValue = minValue;
			minValue = temp;
		}

		return new IntegerValue(minValue + (long) (Math.random() * (maxValue - minValue)));
	}
}