package com.levkopo.vs.library.dialogs;

import com.github.bloodshura.ignitium.charset.TextBuilder;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.function.annotation.MethodVarArgs;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;
import com.github.bloodshura.sparkium.desktop.dialogs.Dialogs;

@MethodName("inputDialog")
@MethodVarArgs
public class InputDialog extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		if (descriptor.isEmpty()) {
			return new StringValue("");
		}

		String title = descriptor.transform(0, Value::toString, null);
		TextBuilder message = new TextBuilder();
		int offset = descriptor.count() > 1 ? 1 : 0;

		for (int i = offset; i < descriptor.count(); i++) {
			message.append(descriptor.get(i));
			message.newLine();
		}

		String input = Dialogs.askInput(title, message);

		return new StringValue(input != null ? input : "");
	}
}