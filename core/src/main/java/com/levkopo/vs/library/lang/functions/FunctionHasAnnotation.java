package com.levkopo.vs.library.lang.functions;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.Function;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.FunctionRefValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

@MethodName("hasAnnotation")
@MethodArgs({FunctionRefValue.class, StringValue.class})
public class FunctionHasAnnotation extends Method {

    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        FunctionRefValue functionRef = (FunctionRefValue) descriptor.get(0);
        Function function =
                context.getOwner().findFunction(context, functionRef.value(), null);
        return new BoolValue(function.hasAnnotation((String) descriptor.get(1).value()));
    }
}
