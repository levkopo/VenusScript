package com.levkopo.vs.expression;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.type.Type;
import com.levkopo.vs.value.Value;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.util.Map;

public interface Expression {

	@Deprecated //issue #24
	default Value resolve(Context context) throws ScriptRuntimeException {
		return resolve(context, context);
	}

	Value resolve(Context context, Context vars_context) throws ScriptRuntimeException;

	default MethodVisitor visit(MethodVisitor visitor, Map<String, Integer> variables){
		return visitor;
	}

	@Deprecated //issue #24
	default Type resolveType(Context context) throws ScriptRuntimeException {
		return resolve(context).getType();
	}

	default Type resolveType(Context context, Context vars_context) throws ScriptRuntimeException {
		return resolve(context, vars_context).getType();
	}
}