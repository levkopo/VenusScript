package com.levkopo.vs.library.engine;

import com.levkopo.vs.component.Script;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.executor.VenusExecutor;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.origin.ScriptMode;
import com.levkopo.vs.origin.ScriptOrigin;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

@MethodArgs(StringValue.class)
@MethodName("run")
public class Run extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		StringValue path = (StringValue) descriptor.get(0);
		Script current = context.getScript();
		ScriptOrigin origin = current.getOrigin().findRelative(path.value());

		if (origin != null) {
			try {
				Script script = origin.compile(current.getApplicationContext());
				VenusExecutor executor =
						new VenusExecutor(context.getApplicationContext().currentExecutor().throwableConsumer);

				executor.run(script, ScriptMode.NORMAL);

				return new BoolValue(true);
			} catch (ScriptCompileException exception) {
				throw new ScriptRuntimeException(context, "Failed to compile script", exception);
			}
		}

		return new BoolValue(false);
	}
}
