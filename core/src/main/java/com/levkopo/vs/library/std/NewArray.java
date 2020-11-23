package com.levkopo.vs.library.std;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.ArrayValue;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.Value;

import java.util.Arrays;

@MethodArgs({ IntegerValue.class, Value.class })
@MethodName("newArray")
public class NewArray extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		IntegerValue size = (IntegerValue) descriptor.get(0);
		Value[] content = new Value[size.value().intValue()];

		Arrays.fill(content, descriptor.get(1));

		return new ArrayValue(content);
	}
}