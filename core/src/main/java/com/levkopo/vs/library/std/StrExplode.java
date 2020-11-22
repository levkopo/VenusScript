package com.levkopo.vs.library.std;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.ArrayValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

@MethodName("explode")
@MethodArgs({StringValue.class, StringValue.class})
public class StrExplode extends Method {

    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        StringValue delimiter = (StringValue) descriptor.get(1);
        StringValue string = (StringValue) descriptor.get(0);

        String[] strings = string.value().split(delimiter.value());
        ArrayValue result = new ArrayValue(strings.length);
        for(String string_: strings)
            result.and(new StringValue(string_));

        return result;
    }
}
