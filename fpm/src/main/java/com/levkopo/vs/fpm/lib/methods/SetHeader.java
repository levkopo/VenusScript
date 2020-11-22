package com.levkopo.vs.fpm.lib.methods;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.executor.OutputReference;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.StringValue;

@MethodArgs({StringValue.class})
@MethodName("setHeader")
public class SetHeader extends VoidMethod {

    @Override
    public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        OutputReference reference = context.getApplicationContext().getUserData("headers", OutputReference.class);
        reference.output((String) descriptor.get(0).value());
    }
}
