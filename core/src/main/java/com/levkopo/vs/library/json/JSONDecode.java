package com.levkopo.vs.library.json;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.*;
import org.json.JSONArray;
import org.json.JSONObject;

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
				return parseJSONArray(new JSONArray(json_string));
			else if(json_string.startsWith("{"))
				return parseJSONObject(new JSONObject(json_string));
		} catch (IOException ignored) {}

		return new BoolValue(false);
	}

	private Value parseJSONArray(JSONArray array) throws IOException {
		Value[] return_array = new Value[array.length()];
		for(int i = 0; i < array.length(); i++){
			Object value = array.get(i);
			if(value instanceof JSONArray)
				return_array[i] = parseJSONArray((JSONArray) value);
			else if(value instanceof JSONObject)
				return_array[i] = parseJSONObject((JSONObject) value);
			else return_array[i] = Value.construct(value);
		}

		return new ArrayValue(return_array);
	}

	private Value parseJSONObject(JSONObject object) throws IOException {
		Map<Object, Value> map = new HashMap<>();
		for(String key: object.keySet()){
			Object value = object.get(key);
			if(value instanceof JSONArray)
				map.put(key, parseJSONArray((JSONArray) value));
			else if(value instanceof JSONObject)
				map.put(key, parseJSONObject((JSONObject) value));
			else map.put(key, Value.construct(value));
		}

		return new MapValue(map);
	}
}
