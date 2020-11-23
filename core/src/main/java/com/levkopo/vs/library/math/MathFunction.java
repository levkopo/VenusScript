package com.levkopo.vs.library.math;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.Function;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.type.PrimitiveType;
import com.levkopo.vs.type.Type;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.Value;
import com.sun.istack.internal.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MathFunction implements Function {
	private final List<Type> arguments;
	private final Method method;
	private final String name;

	public MathFunction(Method method) {
		this.arguments = new ArrayList<>();
		this.method = method;
		this.name = method.getName();

		for (Class<?> arg : method.getParameterTypes()) {
			arguments.add(PrimitiveType.forObjectType(arg));
		}
	}

	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		List<Object> values = new ArrayList<>();
		int i = 0;

		for (Value argument : descriptor.getValues()) {
			if (argument instanceof IntegerValue && method.getParameterTypes()[i] == Integer.class) {
				values.add(((Long) argument.value()).intValue());
			} else {
				values.add(argument.value());
			}

			i++;
		}

		try {
			Object result = getMethod().invoke(null, values.toArray());

			if (getMethod().getReturnType() == void.class && result == null) {
				return null;
			}

			Value value = Value.construct(result);

			if (value == null) {
				throw new ScriptRuntimeException(context, "Math method \"" + method.getName() + "\" returned untranslatable value of type " + result.getClass().getSimpleName() + "?!");
			}

			return value;
		} catch (IllegalAccessException | InvocationTargetException exception) {
			throw new ScriptRuntimeException(context, "Could not call math function \"" + getName() + "\"", exception);
		}
	}

	@Override
	public List<Type> getArgumentTypes() {
		return arguments;
	}

	public Method getMethod() {
		return method;
	}

	@NotNull
	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isVarArgs() {
		return false;
	}

	public static boolean validate(Method method) {
		if (PrimitiveType.forObjectType(method.getReturnType()) == null) {
			return false;
		}

		for (Class<?> arg : method.getParameterTypes()) {
			if (PrimitiveType.forObjectType(arg) == null) {
				return false;
			}
		}

		return true;
	}
}
