package com.levkopo.vs.library.request;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.MapValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

@MethodName("addHeader")
@MethodArgs({StringValue.class, Value.class})
public class SetHeader extends Method {
    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        MapValue headers = (MapValue) context.getVar("headers").getValue();
        headers.getMap().put(descriptor.get(0).value(), descriptor.get(1));

        context.setVar("headers", headers);
        return context.getVar("this").getValue();
    }
}
