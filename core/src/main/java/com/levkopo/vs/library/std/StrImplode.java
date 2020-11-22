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

@MethodName("implode")
@MethodArgs({ArrayValue.class, StringValue.class})
public class StrImplode extends Method {

    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        ArrayValue pieces = (ArrayValue) descriptor.get(0);
        String glue = ((StringValue) descriptor.get(1)).value();

        StringBuilder result = new StringBuilder();
        for(Value value: pieces.value()){
            result.append(value.value()).append(glue);
        }

        return new StringValue(result.toString());
    }
}
