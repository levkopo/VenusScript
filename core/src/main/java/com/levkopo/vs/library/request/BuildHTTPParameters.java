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

@MethodName("buildHttpParameters")
@Deprecated
@MethodArgs({MapValue.class})
public class BuildHTTPParameters extends Method {

    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        MapValue mapValue = (MapValue) descriptor.get(0);
        return new StringValue(URLBuilder.httpBuildQuery(mapValue.getMap()));
    }
}
