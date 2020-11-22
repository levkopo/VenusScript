package com.levkopo.vs.library.std;

import com.github.bloodshura.ignitium.activity.logging.Logger;
import com.github.bloodshura.ignitium.activity.scanning.XScanner;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.Value;

@MethodName("hasScan")
public class HasScan extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		Logger logger = context.getApplicationContext().getUserData("in", Logger.class);

		if (logger != null) {
			return new BoolValue(XScanner.has());
		}

		return new BoolValue(false);
	}
}