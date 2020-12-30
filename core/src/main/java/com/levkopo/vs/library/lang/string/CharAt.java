package com.levkopo.vs.library.lang.string;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

@MethodName("charAt")
@MethodArgs({Value.class})
public class CharAt extends Method {
    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        String value = (String) context.getVar("value").getValue().value();
        Number charNum = (Number) descriptor.get(0).value();
        return new StringValue(String.valueOf(value.charAt(charNum.intValue())));
    }
}
