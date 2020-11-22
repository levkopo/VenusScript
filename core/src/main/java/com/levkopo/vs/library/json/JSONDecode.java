package com.levkopo.vs.library.json;

import com.github.bloodshura.ignitium.cfg.json.JsonArray;
import com.github.bloodshura.ignitium.cfg.json.JsonObject;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.ArrayValue;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.MapValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@MethodName("jsonDecode")
@MethodArgs(StringValue.class)
public class JSONDecode extends Method {

	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		try {
			String json_string = (String) descriptor.get(0).value();
			if(json_string.startsWith("["))
				return parseJSONArray(new JsonArray(json_string));
			else if(json_string.startsWith("{"))
				return parseJSONObject(new JsonObject(json_string));
		} catch (IOException ignored) {}

		return new BoolValue(false);
	}

	private Value parseJSONArray(JsonArray array) throws IOException {
		Value[] return_array = new Value[array.size()];
		for(int i = 0; i < array.size(); i++){
			Object value = array.get(i);
			if(value instanceof JsonArray)
				return_array[i] = parseJSONArray((JsonArray) value);
			else if(value instanceof JsonObject)
				return_array[i] = parseJSONObject((JsonObject) value);
			else return_array[i] = Value.construct(value);
		}

		return new ArrayValue(return_array);
	}

	private Value parseJSONObject(JsonObject object) throws IOException {
		Map<Object, Value> map = new HashMap<>();
		for(int i = 0; i < object.keys().size(); i++){
			String key = object.keys().get(i);
			Object value = object.get(key);
			if(value instanceof JsonArray)
				map.put(key, parseJSONArray((JsonArray) value));
			else if(value instanceof JsonObject)
				map.put(key, parseJSONObject((JsonObject) value));
			else map.put(key, Value.construct(value));
		}

		return new MapValue(map);
	}
}
