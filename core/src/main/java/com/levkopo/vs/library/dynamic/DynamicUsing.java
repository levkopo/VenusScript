package com.levkopo.vs.library.dynamic;

import com.levkopo.vs.component.Script;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.library.VSLibrary;
import com.levkopo.vs.value.StringValue;

import java.util.function.Supplier;

@MethodArgs(StringValue.class)
@MethodName("dynamicUsing")
public class DynamicUsing extends VoidMethod {
	@Override
	public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		StringValue libraryName = (StringValue) descriptor.get(0);
		Script script = context.getScript();
		Supplier<VSLibrary> supplier = script.getApplicationContext().getLibrarySuppliers().get(libraryName.value());
		VSLibrary library;

		if (supplier != null && (library = supplier.get()) != null) {
			script.getLibraryList().add(library);
		} else {
			throw new ScriptRuntimeException(context, "Could not find a library named \"" + libraryName + "\"");
		}
	}
}
