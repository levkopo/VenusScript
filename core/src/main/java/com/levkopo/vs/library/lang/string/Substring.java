package com.levkopo.vs.library.lang.string;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

@MethodName("substring")
@MethodArgs({IntegerValue.class, IntegerValue.class})
public class Substring extends Method {

    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        String value = (String) context.getVar("value").getValue().value();
        Number start = (Number) descriptor.get(0).value();
        Number end = (Number) descriptor.get(1).value();

        return new StringValue(value.substring(start.intValue(), end.intValue()));
    }
}
