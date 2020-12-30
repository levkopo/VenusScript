package com.levkopo.vs.component;

import com.levkopo.vs.component.object.ObjectDefinition;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.exception.runtime.UndefinedFunctionException;
import com.levkopo.vs.exception.runtime.UndefinedValueTypeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.Definition;
import com.levkopo.vs.function.Function;
import com.levkopo.vs.type.Type;
import com.levkopo.vs.value.FunctionRefValue;
import com.levkopo.vs.value.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Container extends Component {
	protected Context context;

	private final List<Component> children = new ArrayList<>();
	private final List<Function> functions = new ArrayList<>();
	private final Map<String, ObjectDefinition> objectDefinitions = new HashMap<>();

	public Function findFunction(Context context, String name, List<Type> argumentTypes) throws ScriptRuntimeException {
		if (context.hasVar(name)) {
			Value value = context.getVarValue(name);
			if (value instanceof FunctionRefValue) {
				FunctionRefValue reference = (FunctionRefValue) value;

				return context.getOwner().findFunction(context, reference.value(), null);
			}
		}

		for(Function function: functions){
			if(function instanceof Definition){
				Definition definition = (Definition) function;
				if (definition.accepts(name, argumentTypes)) {
					return definition;
				}
			}
		}

		if (hasParent()) {
			try {
				return getParent().findFunction(context, name, argumentTypes);
			} catch (UndefinedFunctionException ignored) {}
		}

		throw new UndefinedFunctionException(context, name, argumentTypes);
	}

	public ObjectDefinition findObjectDefinition(Context context, String name) throws ScriptRuntimeException {
		if(objectDefinitions.containsKey(name)){
			return objectDefinitions.get(name);
		}

		if (hasParent()) {
			try {
				return getParent().findObjectDefinition(context, name);
			} catch (UndefinedValueTypeException ignored) {}
		}

		throw new UndefinedValueTypeException(context, name);
	}

	public Type findType(Context context, String name) throws ScriptRuntimeException {
		if(objectDefinitions.containsKey(name)){
			return objectDefinitions.get(name).getType();
		}

		if (hasParent()) {
			try {
				return getParent().findType(context, name);
			} catch (UndefinedValueTypeException ignored) {}
		}

		throw new UndefinedValueTypeException(context, name);
	}

	public List<Function> getFunctions() {
		return functions;
	}

	public List<Component> getChildren() {
		return children;
	}

	public void addChildren(Component component) {
		component.setParent(this);

		if(component instanceof Function)
			functions.add((Function) component);
		else if(component instanceof ObjectDefinition){
			ObjectDefinition definition = (ObjectDefinition) component;
			objectDefinitions.put(definition.getName(), definition);
		}else children.add(component);
	}

	public Context getContext() {
		return context;
	}

	@Override
	public void setParent(Container parent) {
		super.setParent(parent);
		this.context = parent.getContext();
	}
}
