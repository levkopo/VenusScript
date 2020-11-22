package com.levkopo.vs.expression;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.value.MapValue;
import com.levkopo.vs.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MapLiteral implements Expression {

	public final Map<Value, Expression> map;

	public MapLiteral(Map<Value, Expression> map) {
		this.map = map;
	}

	@Override
	public Value resolve(Context context, Context vars_context) throws ScriptRuntimeException {
		Map<Object, Value> map_ = new HashMap<>();
		for(Value key: map.keySet()){
			Expression value = map.get(key);
			map_.put(key.value(), value.resolve(context, vars_context));
		}

		return new MapValue(map_);
	}

	@Override
	public String toString() {
		return map.toString();
	}
}
