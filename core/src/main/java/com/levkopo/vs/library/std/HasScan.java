package com.levkopo.vs.library.std;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.Value;

import java.util.Scanner;

@MethodName("hasScan")
public class HasScan extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		Scanner scanner = context.getApplicationContext().getUserData("in", Scanner.class);

		if (scanner != null) {
			return new BoolValue(scanner.hasNext());
		}

		return new BoolValue(false);
	}
}