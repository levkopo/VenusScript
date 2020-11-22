package com.levkopo.vs.expression;

import com.levkopo.vs.exception.runtime.InvalidValueTypeException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.value.MapValue;
import com.levkopo.vs.value.ObjectValue;
import com.levkopo.vs.value.Value;

public class InContext implements Expression {
	private final Expression expression;
	private final String name;

	public InContext(String name, Expression expression) {
		this.name = name;
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	public String getName() {
		return name;
	}


	@Override
	public Value resolve(Context context, Context vars_context) throws ScriptRuntimeException {
		Value value = context.getVarValue(getName());

		if (value instanceof ObjectValue) {
			ObjectValue object = (ObjectValue) value;

			return getExpression().resolve(object.getContext(), vars_context);
		} else if(value instanceof MapValue){
			MapValue map = (MapValue) value;

			
			if(getExpression() instanceof InContext){

			}
			return map.get(vars_context, getExpression().resolve(context, vars_context));
		}else {
			throw new InvalidValueTypeException(context, getName() + " has type " + value.getType() + "; expected to be an object or map");
		}
	}

	@Override
	public String toString() {
		return "incontext(" + getName() + " << " + getExpression() + ')';
	}
}
