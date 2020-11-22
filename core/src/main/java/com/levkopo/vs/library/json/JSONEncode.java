package com.levkopo.vs.library.json;

import com.github.bloodshura.ignitium.cfg.json.JsonArray;
import com.github.bloodshura.ignitium.cfg.json.JsonObject;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.function.annotation.MethodVarArgs;
import com.levkopo.vs.value.ArrayValue;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.MapValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

@MethodName("jsonEncode")
@MethodVarArgs
public class JSONEncode extends Method {

	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		Value value = descriptor.get(0);
		if(value instanceof ArrayValue)
			return new StringValue(parseArray((ArrayValue) value).toString(false));
		else if(value instanceof MapValue)
			return new StringValue(parseMap((MapValue) value).toString(false));

		return new BoolValue(false);
	}

	public JsonArray parseArray(ArrayValue array){
		JsonArray array_ = new JsonArray();
		for (Value value: array){
			Object value_ = value.value();
			if(value instanceof ArrayValue)
				value_ = parseArray((ArrayValue) value);
			else if(value instanceof MapValue)
				value_ = parseMap((MapValue) value);

			array_.add(value_);
		}

		return array_;
	}

	public JsonObject parseMap(MapValue map){
		JsonObject object = new JsonObject();
		for(Object key: map.getMap().keySet()){
			Value value = map.getMap().get(key);
			Object value_ = value.value();
			if(value instanceof ArrayValue)
				value_ = parseArray((ArrayValue) value);
			else if(value instanceof MapValue)
				value_ = parseMap((MapValue) value);

			object.set(key.toString(), value_);
		}

		return object;
	}
}
