package com.levkopo.vs.library.json;

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
import org.json.JSONArray;
import org.json.JSONObject;

@MethodName("jsonEncode")
@MethodVarArgs
public class JSONEncode extends Method {

	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		Value value = descriptor.get(0);

		try {
			if (value instanceof ArrayValue)
				return new StringValue(parseArray((ArrayValue) value).toString());
			else if (value instanceof MapValue)
				return new StringValue(parseMap((MapValue) value).toString());
		}catch (Exception ignored){}

		return new BoolValue(false);
	}

	public JSONArray parseArray(ArrayValue array){
		JSONArray array_ = new JSONArray();
		for (Value value: array){
			Object value_ = value.value();
			if(value instanceof ArrayValue)
				value_ = parseArray((ArrayValue) value);
			else if(value instanceof MapValue)
				value_ = parseMap((MapValue) value);

			array_.put(value_);
		}

		return array_;
	}

	public JSONObject parseMap(MapValue map){
		JSONObject object = new JSONObject();
		for(Object key: map.getMap().keySet()){
			Value value = map.getMap().get(key);
			Object value_ = value.value();
			if(value instanceof ArrayValue)
				value_ = parseArray((ArrayValue) value);
			else if(value instanceof MapValue)
				value_ = parseMap((MapValue) value);

			object.put(key.toString(), value_);
		}

		return object;
	}
}
