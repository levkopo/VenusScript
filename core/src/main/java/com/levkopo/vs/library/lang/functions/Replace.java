package com.levkopo.vs.library.lang.functions;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

@MethodName("string_replace")
@MethodArgs({StringValue.class, StringValue.class, StringValue.class})
public class Replace extends Method {

    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        String search = ((StringValue) descriptor.get(0)).value();
        String replace = ((StringValue) descriptor.get(1)).value();
        String subject = ((StringValue) descriptor.get(2)).value();

        return new StringValue(subject.replaceAll(search, replace));
    }
}
