package com.levkopo.vs.library.lang.functions;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.executor.VenusExecutor;
import com.levkopo.vs.function.Definition;
import com.levkopo.vs.function.Function;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.origin.ScriptMode;
import com.levkopo.vs.value.FunctionRefValue;

import java.util.ArrayList;

@MethodName("runOnThread")
@MethodArgs({FunctionRefValue.class})
public class RunOnThread extends VoidMethod {

    @Override
    public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        FunctionRefValue functionRef = (FunctionRefValue) descriptor.get(0);
        Function function = context.getOwner().findFunction(context, functionRef.value(), new ArrayList<>());
        if(!(function instanceof Definition)||function.getArgumentCount()!=0){
            throw new ScriptRuntimeException(context, "Invalid function");
        }

        new Thread(()->{
            try {
                context.getApplicationContext().currentExecutor()
                        .run((Definition) function, ScriptMode.NORMAL);
            } catch (ScriptRuntimeException ignored) {}
        }).start();
    }
}
