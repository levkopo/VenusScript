package com.levkopo.vs.expression;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.type.Type;
import com.levkopo.vs.value.Value;

public interface Expression {

	Value resolve(Context context, Context vars_context) throws ScriptRuntimeException;

	default Type resolveType(Context context, Context vars_context) throws ScriptRuntimeException {
		return resolve(context, vars_context).getType();
	}
}