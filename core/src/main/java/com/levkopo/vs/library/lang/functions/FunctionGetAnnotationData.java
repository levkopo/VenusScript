package com.levkopo.vs.library.lang.functions;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.Definition;
import com.levkopo.vs.function.Function;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.*;

import java.util.List;

@MethodName("getAnnotationData")
@MethodArgs({FunctionRefValue.class, StringValue.class})
public class FunctionGetAnnotationData extends Method {

    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        FunctionRefValue functionRef = (FunctionRefValue) descriptor.get(0);
        Function function =
                context.getOwner().findFunction(context, functionRef.value(), null);

        if(function instanceof Definition){
            Definition definition = (Definition) function;
            List<Value> values = ((Definition) function)
                    .getAnnotation((String) descriptor.get(1).value())
                    .data;
            return new ArrayValue(values.toArray(new Value[0]));
        }


        return new BoolValue(false);
    }
}
