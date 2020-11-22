package com.levkopo.vs.library.std;

import com.github.bloodshura.ignitium.io.Url;
import com.github.bloodshura.ignitium.io.UrlException;
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

@MethodArgs(StringValue.class)
@MethodName("browse")
public class Browse extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		StringValue path = (StringValue) descriptor.get(0);

		try {
			XSystem.getDesktop().browse(new Url(path.value()));

			return new BoolValue(true);
		} catch (UrlException exception) {
			return new BoolValue(false);
		}
	}
}
