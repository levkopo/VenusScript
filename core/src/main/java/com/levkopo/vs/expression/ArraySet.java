package com.levkopo.vs.expression;

import com.levkopo.vs.exception.runtime.InvalidArrayAccessException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.type.PrimitiveType;
import com.levkopo.vs.value.ArrayValue;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.MapValue;
import com.levkopo.vs.value.Value;

public class ArraySet implements Expression {
	private final String name;
	private final Expression index;
	private final Expression expression;

	public ArraySet(String name, Expression index, Expression expression) {
		this.index = index;
		this.name = name;
		this.expression = expression;
	}

	public Expression getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public Value resolve(Context context, Context vars_context) throws ScriptRuntimeException {
		Value value = vars_context.getVarValue(getName());

		if (value instanceof ArrayValue) {
			ArrayValue array = (ArrayValue) value;
			Value index = getIndex().resolve(context, vars_context);

			if (index instanceof IntegerValue) {
				IntegerValue intIndex = (IntegerValue) index;
				Value result = getExpression().resolve(context, vars_context);

				array.set(intIndex.value().intValue(), result);

				return result;
			}

			throw new InvalidArrayAccessException(context, "Index \"" + index + "\" is of type " + index.getType() + "; expected to be an " + PrimitiveType.INTEGER);
		}else if(value instanceof MapValue){
			MapValue map = (MapValue) value;
			Value index = getIndex().resolve(context, vars_context);
			Value result = getExpression().resolve(context, vars_context);

			map.getMap().put(index.value(), result);
			return result;
		}

		throw new InvalidArrayAccessException(context, "Variable \"" + getName() + "\" is of type " + value.getType() + "; expected to be an " + PrimitiveType.ARRAY);
	}

	@Override
	public String toString() {
		return "arrayAttribution(" + getName() + '[' + getIndex() + "]=" + getExpression() + ')';
	}
}