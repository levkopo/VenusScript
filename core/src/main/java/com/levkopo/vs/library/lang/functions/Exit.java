package com.levkopo.vs.library.lang.functions;

import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodName;

@MethodName("exit")
public class Exit extends VoidMethod {
    @Override
    public void callVoid(Context context, FunctionCallDescriptor descriptor) {
        context.getApplicationContext().currentExecutor().stop();
    }
}
