package com.levkopo.vs.library.std;

import com.github.bloodshura.ignitium.sys.XSystem;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

import java.io.IOException;

@MethodArgs({ StringValue.class, BoolValue.class })
@MethodName("shell")
public class Shell extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		StringValue command = (StringValue) descriptor.get(0);
		BoolValue newWindow = (BoolValue) descriptor.get(1);

		try {
			XSystem.getTerminal().runInShell(command.value(), newWindow.value());

			return new BoolValue(true);
		} catch (IOException exception) {
			return new BoolValue(false);
		}
	}
}
