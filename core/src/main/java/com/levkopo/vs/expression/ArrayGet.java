package com.levkopo.vs.expression;

import com.levkopo.vs.exception.runtime.InvalidArrayAccessException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.type.PrimitiveType;
import com.levkopo.vs.value.ArrayValue;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.MapValue;
import com.levkopo.vs.value.Value;

public class ArrayGet implements Expression {
	private final Expression index;
	private final String name;

	public ArrayGet(String name, Expression index) {
		this.index = index;
		this.name = name;
	}

	public Expression getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	@Override
	public Value resolve(Context context, Context vars_context) throws ScriptRuntimeException {
		Value value = vars_context.getVarValue(getName());
		Value index = getIndex().resolve(context, vars_context);

		if (value instanceof ArrayValue) {
			ArrayValue array = (ArrayValue) value;

			if (index instanceof IntegerValue) {
				IntegerValue intIndex = (IntegerValue) index;

				return array.get(vars_context, intIndex.value().intValue());
			}

			throw new InvalidArrayAccessException(context, "Index \"" + index + "\" is of type " + index.getType() + "; expected to be an " + PrimitiveType.INTEGER);
		}else if(value instanceof MapValue) {
			MapValue map = (MapValue) value;
			return map.get(vars_context, index);
		}

		throw new InvalidArrayAccessException(context, "Variable \"" + getName() + "\" is of type " + value.getType() + "; expected to be an " + PrimitiveType.ARRAY);
	}

	@Override
	public String toString() {
		return "arr(" + getName() + '[' + getIndex() + "])";
	}
}
