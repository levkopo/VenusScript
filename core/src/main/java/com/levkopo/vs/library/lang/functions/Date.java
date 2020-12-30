package com.levkopo.vs.library.lang.functions;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.TimeZone;

@MethodName("date")
@MethodArgs({StringValue.class, Value.class})
public class Date extends Method {

    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        Number num = (Number) descriptor.get(1).value();
        SimpleDateFormat dateFormat = new SimpleDateFormat((String) descriptor.get(0).value());
        dateFormat.setTimeZone(
                TimeZone.getTimeZone(context.getApplicationContext()
                        .getUserData("timezone", ZoneId.class)));
        return new StringValue(dateFormat.format(new java.util.Date(num.longValue()*1000)));
    }
}
