package com.github.bloodshura.ignitium.venus.library.json;

import com.github.bloodshura.ignitium.cfg.json.JsonArray;
import com.github.bloodshura.ignitium.cfg.json.JsonObject;
import com.github.bloodshura.ignitium.venus.exception.runtime.ScriptRuntimeException;
import com.github.bloodshura.ignitium.venus.executor.Context;
import com.github.bloodshura.ignitium.venus.function.FunctionCallDescriptor;
import com.github.bloodshura.ignitium.venus.function.Method;
import com.github.bloodshura.ignitium.venus.function.annotation.MethodArgs;
import com.github.bloodshura.ignitium.venus.function.annotation.MethodName;
import com.github.bloodshura.ignitium.venus.value.ArrayValue;
import com.github.bloodshura.ignitium.venus.value.BoolValue;
import com.github.bloodshura.ignitium.venus.value.MapValue;
import com.github.bloodshura.ignitium.venus.value.StringValue;
import com.github.bloodshura.ignitium.venus.value.Value;

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
