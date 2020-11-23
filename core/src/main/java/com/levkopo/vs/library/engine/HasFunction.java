package com.levkopo.vs.library.engine;

import com.levkopo.vs.exception.runtime.InvalidFunctionParameterException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.exception.runtime.UndefinedFunctionException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.function.annotation.MethodVarArgs;
import com.levkopo.vs.type.Type;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.TypeValue;
import com.levkopo.vs.value.Value;

import java.util.ArrayList;
import java.util.List;

@MethodName("hasFunction")
@MethodVarArgs
public class HasFunction extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		if (descriptor.isEmpty()) {
			throw new InvalidFunctionParameterException(context, "Expected at least function name");
		}

		String name = descriptor.get(0).toString();
		List<Type> types = new ArrayList<>();

		for (int i = 1; i < descriptor.count(); i++) {
			Value value = descriptor.get(i);

			if (value instanceof TypeValue) {
				TypeValue typeValue = (TypeValue) value;

				types.add(typeValue.value());
			} else {
				throw new InvalidFunctionParameterException(context, "Expected value type, received " + value.getType());
			}
		}

		try {
			context.getOwner().findFunction(context, name, types);

			return new BoolValue(true);
		} catch (UndefinedFunctionException exception) {
			return new BoolValue(false);
		}
	}
}
