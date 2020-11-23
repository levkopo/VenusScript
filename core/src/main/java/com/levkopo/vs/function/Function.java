package com.levkopo.vs.function;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.type.PrimitiveType;
import com.levkopo.vs.type.Type;
import com.levkopo.vs.value.Value;

import java.util.List;

public interface Function {

	default boolean accepts(String name, List<Type> argumentTypes) {
		if (getName().equals(name)) {
			if (argumentTypes == null) {
				return true;
			}

			if (getArgumentCount() == argumentTypes.size()) {
				for (int i = 0; i < getArgumentCount(); i++) {
					Type required = getArgumentTypes().get(i);
					Type found = argumentTypes.get(i);

					if (!required.accepts(found) && (required != PrimitiveType.DECIMAL || found != PrimitiveType.INTEGER)) {
						return false;
					}
				}

				return true;
			} else return isVarArgs();
		}

		return false;
	}

	String getName();

	Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException;

	default int getArgumentCount() {
		return getArgumentTypes().size();
	}

	List<Type> getArgumentTypes();

	boolean isVarArgs();
}
