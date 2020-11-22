package com.levkopo.vs.fpm.lib.methods;

import com.levkopo.vs.exception.runtime.UndefinedVariableException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.NullValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

import java.util.Map;

@MethodName("getQuery")
@MethodArgs({Value.class})
public class GetQuery extends Method {
    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws UndefinedVariableException {
        String name = descriptor.get(0).value().toString();
        Map<String, String> query_list = context.getApplicationContext().getUserData("query", Map.class);

        if(query_list.containsKey(name))
            return new StringValue(query_list.get(name));

        return new NullValue();
    }
}
