package com.levkopo.vs.library.lang.functions;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.StringValue;

import java.time.ZoneId;

@MethodName("setTimezone")
@MethodArgs({StringValue.class})
public class SetTimezone extends VoidMethod {

    @Override
    public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        StringValue stringValue = (StringValue) descriptor.get(0);
        context.getApplicationContext().setUserData("timezone",
                ZoneId.of(stringValue.value()));
    }
}
