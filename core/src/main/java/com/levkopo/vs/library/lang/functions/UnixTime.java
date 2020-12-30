package com.levkopo.vs.library.lang.functions;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.Value;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@MethodName("time")
public class UnixTime extends Method {
    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        return new IntegerValue(ZonedDateTime
                .now(context.getApplicationContext().getUserData("timezone", ZoneId.class))
                .toEpochSecond());
    }
}
