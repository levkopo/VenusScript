package com.github.bloodshura.ignitium.venus.expression;

import com.github.bloodshura.ignitium.venus.exception.runtime.ScriptRuntimeException;
import com.github.bloodshura.ignitium.venus.executor.Context;
import com.github.bloodshura.ignitium.venus.value.MapValue;
import com.github.bloodshura.ignitium.venus.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MapLiteral implements Expression {

	public final Map<Value, Expression> map;

	public MapLiteral(Map<Value, Expression> map) {
		this.map = map;
	}

	@Override
	public Value resolve(Context context) throws ScriptRuntimeException {
		Map<Object, Value> map_ = new HashMap<>();
		for(int i = 0; i<map.size(); i++){
			Value key = (Value) map.keySet().toArray()[(i)];
			Expression value = map.get(map.keySet().toArray()[(i)]);
			map_.put(key.value(), value.resolve(context));
		}

		return new MapValue(map_);
	}

	@Override
	public String toString() {
		return map.toString();
	}
}
