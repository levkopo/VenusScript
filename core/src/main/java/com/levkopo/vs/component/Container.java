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
import java.util.List;

public abstract class Container extends Component {
	protected Context context;
	private final List<Component> children;

	public Container() {
		this.children = new ArrayList<>();
	}

	public Function findFunction(Context context, String name, List<Type> argumentTypes) throws ScriptRuntimeException {
		if (context.hasVar(name)) {
			Value value = context.getVarValue(name); // Should not need to catch UndefinedVariableException since we already
			// checked that the variable exists
			if (value instanceof FunctionRefValue) {
				FunctionRefValue reference = (FunctionRefValue) value;

				return context.getOwner().findFunction(context, reference.value(), null);
			}
		}

		Definition foundVarArgs = null;

		for(Component component: getChildren()){
			if(component instanceof Definition){
				Definition definition = (Definition) component;
				if (definition.accepts(name, argumentTypes)) {
					if (definition.isVarArgs()) {
						foundVarArgs = definition;
					} else {
						return definition;
					}
				}
			}
		}

		if (foundVarArgs != null) {
			return foundVarArgs;
		}

		if (hasParent()) {
			try {
				return getParent().findFunction(context, name, argumentTypes);
			} catch (UndefinedFunctionException ignored) {
			}
		}

		throw new UndefinedFunctionException(context, name, argumentTypes);
	}

	public ObjectDefinition findObjectDefinition(Context context, String name) throws ScriptRuntimeException {
		for(Component component: getChildren()){
			if(component instanceof ObjectDefinition){
				ObjectDefinition definition = (ObjectDefinition) component;
				if (definition.getName().equals(name)) {
					return definition;
				}
			}
		}

		if (hasParent()) {
			try {
				return getParent().findObjectDefinition(context, name);
			} catch (UndefinedValueTypeException ignored) {
			}
		}

		//context.getApplicationContext()

		throw new UndefinedValueTypeException(context, name);
	}

	public Type findType(Context context, String name) throws ScriptRuntimeException {
		for(Component component: getChildren()){
			if(component instanceof ObjectDefinition){
				ObjectDefinition definition = (ObjectDefinition) component;
				if (definition.getName().equals(name)) {
					return definition.getType();
				}
			}
		}

		if (hasParent()) {
			try {
				return getParent().findType(context, name);
			} catch (UndefinedValueTypeException ignored) {
			}
		}

		throw new UndefinedValueTypeException(context, name);
	}

	public List<Component> getChildren() {
		return children;
	}

	public void addChildren(Component component) {
		component.setParent(this);
		children.add(component);
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
