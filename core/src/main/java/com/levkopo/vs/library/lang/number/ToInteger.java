package com.levkopo.vs.library.lang.number;

import com.levkopo.vs.exception.runtime.InvalidValueTypeException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.DecimalValue;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

@MethodName("toInt")
public class ToInteger extends Method {

    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        Value value = context.getVar("value").getValue();

        Number number;
        if(value instanceof StringValue){
            number = Long.valueOf(((StringValue) value).value());
        }else if(value instanceof DecimalValue){
            number = ((DecimalValue) value).value();
        }else if(value instanceof IntegerValue){
            number = ((IntegerValue) value).value();
        }else throw new InvalidValueTypeException(context, "Number class not supported "+value.getType().getIdentifier());

        return new IntegerValue(number.longValue());
    }
}
