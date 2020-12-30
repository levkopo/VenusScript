package com.levkopo.vs.library;

import com.levkopo.vs.component.object.Attribute;
import com.levkopo.vs.component.object.ObjectDefinition;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.exception.runtime.UndefinedFunctionException;
import com.levkopo.vs.executor.ApplicationContext;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.Function;
import com.levkopo.vs.type.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ObjectClass extends ObjectDefinition {

    private final List<Function> functions = new ArrayList<>();

    public ObjectClass(String name, Attribute... attrs) {
        super(name);
        getAttributes().addAll(Arrays.asList(attrs));
    }

    public void addFunction(Function function){
        functions.add(function);
    }

    @Override
    public Function findFunction(Context context, String name, List<Type> argumentTypes) throws ScriptRuntimeException {
        for(Function function: functions){
            if(function.accepts(name, argumentTypes))
                return function;
        }

        throw new UndefinedFunctionException(getContext(), name, argumentTypes);
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return null;
    }
}
