package com.levkopo.vs.library.engine;

import com.levkopo.vs.compiler.VenusLexer;
import com.levkopo.vs.compiler.VenusParser;
import com.levkopo.vs.component.SimpleContainer;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.ApplicationContext;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.function.annotation.MethodVarArgs;
import com.levkopo.vs.origin.ScriptMode;
import com.levkopo.vs.origin.SimpleScriptOrigin;

import java.io.IOException;

@MethodName("interpret")
@MethodVarArgs
public class Interpret extends VoidMethod {
	@Override
	public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		VenusParser parser = context.getScript().getParser();

		String source = (descriptor.getValues() + "\n").trim();
		ApplicationContext appContext = context.getApplicationContext();
		SimpleScriptOrigin origin = new SimpleScriptOrigin("Interpreted-Script", source);
		SimpleContainer container = new SimpleContainer();

		container.setParent(context.getOwner());

		try {
			parser.parse(new VenusLexer(origin), container);
			appContext.currentExecutor().run(container, ScriptMode.INTERACTIVE);
		} catch (IOException | ScriptCompileException exception) {
			throw new ScriptRuntimeException(context, "Failed to compile script", exception);
		}
	}
}
