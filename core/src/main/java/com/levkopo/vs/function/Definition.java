package com.levkopo.vs.function;

import com.levkopo.vs.component.Annotation;
import com.levkopo.vs.component.Container;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.origin.ScriptMode;
import com.levkopo.vs.type.Type;
import com.levkopo.vs.value.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Definition extends Container implements Function {
	private final List<Argument> arguments;
	private final boolean global;
	private final String name;
	private final Type returnType;
	private final Map<String, Annotation> annotations = new HashMap<>();

	public Definition(String name, List<Argument> arguments, boolean global, Type returnType) {
		this.arguments = arguments;
		this.global = global;
		this.name = name;
		this.returnType = returnType;
	}

	public Definition(String name, List<Argument> arguments, boolean global, Type returnType, Map<String, Annotation> annotations) {
		this.arguments = arguments;
		this.global = global;
		this.annotations.putAll(annotations);
		this.name = name;
		this.returnType = returnType;
	}

	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		int i = 0;

		if (!isGlobal()){
			this.context = new Context(this, context);
		}

		for (Argument argument : getArguments()) {
			getContext().setVar(argument.getName(), descriptor.get(i++));
		}

		return getApplicationContext().currentExecutor().run(this, ScriptMode.NORMAL);
	}

	@Override
	public int getArgumentCount() { // Default impl. of getArgumentCount() calls getArgumentTypes(), but our impl. is expensive
		return arguments.size();
	}

	public Annotation getAnnotation(String name){
		return annotations.get(name);
	}

	@Override
	public boolean hasAnnotation(String name) {
		return annotations.containsKey(name);
	}

	@Override
	public List<Type> getArgumentTypes() {
		List<Type> types = new ArrayList<>();
		for(Argument argument: arguments){
			types.add(argument.getType());
		}

		return types;
	}

	public List<Argument> getArguments() {
		return arguments;
	}

	public Type getReturnType() {
		return returnType;
	}

	@Override
	public String getName() {
		return name;
	}

	public boolean isGlobal() {
		return global;
	}

	@Override
	public boolean isVarArgs() {
		return false;
	}

	@Override
	public void setParent(Container parent) {
		super.setParent(parent);
		this.context = new Context(this, parent.getContext());
	}

	@Override
	public String toString() {
		return "definition(" + getName() + ')';
	}
}
