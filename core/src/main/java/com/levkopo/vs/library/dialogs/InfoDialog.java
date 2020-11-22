package com.levkopo.vs.library.dialogs;

import com.github.bloodshura.ignitium.charset.TextBuilder;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.function.annotation.MethodVarArgs;
import com.levkopo.vs.value.Value;
import com.github.bloodshura.sparkium.desktop.dialogs.AlertType;
import com.github.bloodshura.sparkium.desktop.dialogs.Dialogs;

@MethodName("infoDialog")
@MethodVarArgs
public class InfoDialog extends VoidMethod {
	@Override
	public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		if (descriptor.isEmpty()) {
			return;
		}

		String title = descriptor.transform(0, Value::toString, null);
		TextBuilder message = new TextBuilder();
		int offset = descriptor.count() > 1 ? 1 : 0;

		for (int i = offset; i < descriptor.count(); i++) {
			message.append(descriptor.get(i));
			message.newLine();
		}

		Dialogs.show(AlertType.INFORMATION, title, message);
	}
}