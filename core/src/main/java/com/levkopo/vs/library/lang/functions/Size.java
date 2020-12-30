package com.levkopo.vs.library.lang.functions;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.*;

import java.util.Map;

@MethodArgs(Value.class)
@MethodName("size")
public class Size extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		Value value= descriptor.get(0);
		int size = 0;
		if(value instanceof ArrayValue) {
			ArrayValue array = (ArrayValue) value;
			size = array.size();
		}else if(value instanceof MapValue){
			MapValue map = (MapValue) value;
			size = map.getMap().size();
		}else if(value instanceof StringValue){
			StringValue string = (StringValue) value;
			size = string.value().length();
		}

		return new IntegerValue(size);
	}
}