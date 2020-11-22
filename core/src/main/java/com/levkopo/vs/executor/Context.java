package com.levkopo.vs.executor;

import com.github.bloodshura.ignitium.collection.map.XMap;
import com.github.bloodshura.ignitium.collection.map.impl.XLinkedMap;
import com.github.bloodshura.ignitium.util.XApi;
import com.levkopo.vs.component.Container;
import com.levkopo.vs.component.Script;
import com.levkopo.vs.exception.runtime.UndefinedVariableException;
import com.levkopo.vs.expression.Constant;
import com.levkopo.vs.expression.Variable;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.NullValue;
import com.levkopo.vs.value.Value;

public class Context implements Cloneable {
	private static int NEXT_ID = 0;
	private final int id;
	private final Container owner;
	private final Context parent;
	private final XMap<String, VariableStructure> variables;

	public Context(Container owner, Context parent) {
		this.id = NEXT_ID++;
		this.owner = owner;
		this.parent = parent;
		this.variables = new XLinkedMap<>();
		this.variables.add("null", new VariableStructure(new NullValue()));
	}

	@Override
	public Context clone() {
		return cloneWith(getOwner(), getParent());
	}

	public Context cloneWith(Container owner, Context parent) {
		Context context = new Context(owner, parent);

		context.getVariables().addAll(getVariables());

		return context;
	}

	public ApplicationContext getApplicationContext() {
		return getOwner().getApplicationContext();
	}

	public Container getOwner() {
		return owner;
	}

	public Context getParent() {
		return parent;
	}

	public Script getScript() {
		return getOwner().getScript();
	}

	public VariableStructure getVar(String name) throws UndefinedVariableException {
		if (name.length() > 1 && name.charAt(0) == '$') {
			return getApplicationContext().getVar(name.substring(1));
		}

		if (isOwnerOf(name)) {
			return getVariables().get(name);
		}

		if (hasParent()) {
			try {
				return getParent().getVar(name);
			} catch (UndefinedVariableException ignored) { }
		}

		if(getOwner()!=null){
			try {
				if(getOwner().getContext().hasVar(name))
					return getOwner().getContext().getVar(name);
			} catch (UndefinedVariableException ignored) { }
		}

		throw new UndefinedVariableException(this, name);
	}

	public VariableStructure getVar(Variable variable) throws UndefinedVariableException {
		return getVar(variable.getName());
	}

	public Value getVarValue(String name) throws UndefinedVariableException {
		XApi.requireNonNull(name, "name");

		return getVar(name).getValue();
	}

	public Value getVarValue(Variable variable) throws UndefinedVariableException {
		return getVarValue(variable.getName());
	}

	public XMap<String, VariableStructure> getVariables() {
		return variables;
	}

	public boolean hasParent() {
		return getParent() != null;
	}

	public boolean hasVar(String name) throws UndefinedVariableException {
		if (name.length() > 1 && name.charAt(0) == '$') {
			return getApplicationContext().hasVar(name.substring(1));
		}

		return isOwnerOf(name) || (hasParent() && getParent().hasVar(name));
	}

	public boolean isOwnerOf(String name) {
		return getVariables().containsKey(name);
	}

	public void setVar(String name, Value value) {
		if (!changeVar(name, value)) {
			getVariables().add(name, new VariableStructure(value));
		}
	}

	public String toDetailedString() {
		return "context(owner=" + getOwner() + ", vars=" + getVariables() + ", parent=" + getParent() + ')';
	}

	@Override
	public String toString() {
		return "#" + id;
	}

	protected boolean changeVar(String name, Value value) {
		if (name.length() > 1 && name.charAt(0) == '$') {
			getOwner().getApplicationContext().setVar(name.substring(1), value);

			return true;
		}

		if (isOwnerOf(name)) {
			getVariables().get(name).setValue(value);

			return true;
		}

		return hasParent() && getParent().changeVar(name, value);
	}
}
