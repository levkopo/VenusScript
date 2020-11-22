package com.levkopo.vs.library.time;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.Value;

import java.time.LocalTime;

@MethodName("getMinute")
public class GetMinute extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		return new IntegerValue(LocalTime.now().getMinute());
	}
}