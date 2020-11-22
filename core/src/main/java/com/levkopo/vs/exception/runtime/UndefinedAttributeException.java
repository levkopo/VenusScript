package com.levkopo.vs.exception.runtime;

import com.levkopo.vs.component.object.ObjectDefinition;
import com.levkopo.vs.executor.Context;

public class UndefinedAttributeException extends ScriptRuntimeException {
	public UndefinedAttributeException(Context context, ObjectDefinition definition, String attributeName) {
		super(context, "No attribute named \"" + attributeName + "\" in object " + definition.getName());
	}
}