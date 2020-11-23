package com.levkopo.vs.library.engine;

import com.levkopo.vs.compiler.VenusLexer;
import com.levkopo.vs.compiler.VenusParser;
import com.levkopo.vs.component.SimpleContainer;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.ApplicationContext;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.function.annotation.MethodVarArgs;
import com.levkopo.vs.origin.ScriptMode;
import com.levkopo.vs.origin.SimpleScriptOrigin;
import com.levkopo.vs.value.Value;

import java.io.IOException;

@MethodName("evaluate")
@MethodVarArgs
public class Evaluate extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		VenusParser parser = context.getScript().getParser();
		StringBuilder builder = new StringBuilder();

		builder.append(descriptor.getValues()).append("\n");

		String source = builder.toString().trim();
		ApplicationContext appContext = context.getApplicationContext();
		SimpleScriptOrigin origin = new SimpleScriptOrigin("Interpreted-Script", source);
		SimpleContainer container = new SimpleContainer();

		container.setParent(context.getOwner());

		try {
			parser.parse(new VenusLexer(origin), container);

			return appContext.currentExecutor().run(container, ScriptMode.EVALUATION);
		} catch (IOException | ScriptCompileException exception) {
			throw new ScriptRuntimeException(context, "Failed to compile script", exception);
		}
	}
}
