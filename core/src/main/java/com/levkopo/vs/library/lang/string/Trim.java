package com.levkopo.vs.library.lang.string;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.StringValue;

@MethodName("trim")
public class Trim extends VoidMethod {

    @Override
    public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        String value = (String) context.getVar("value").getValue().value();
        context.setVar("value", new StringValue(value.trim()));
    }
}
