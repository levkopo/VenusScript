package com.levkopo.vs.library.dialogs;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@MethodArgs(StringValue.class)
@MethodName("setTheme")
public class SetTheme extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		StringValue value = (StringValue) descriptor.get(0);
		String themeName = value.value();
		String themePath = null;

		if (themeName.equalsIgnoreCase("metal")) {
			themePath = UIManager.getCrossPlatformLookAndFeelClassName();
		} else if (themeName.equalsIgnoreCase("system")) {
			themePath = UIManager.getSystemLookAndFeelClassName();
		}

		if (themePath != null) {
			try {
				UIManager.setLookAndFeel(themePath);

				return new BoolValue(true);
			} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ignored) {
			}
		}

		return new BoolValue(false);
	}
}
