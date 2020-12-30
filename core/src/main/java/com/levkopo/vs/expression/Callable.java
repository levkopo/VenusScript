package com.levkopo.vs.expression;

import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.Definition;
import com.levkopo.vs.value.CallableValue;
import com.levkopo.vs.value.Value;

public class Callable implements Expression {

    private final Definition definition;

    public Callable(Definition definition) {
        this.definition = definition;
    }

    @Override
    public Value resolve(Context context, Context vars_context) {
        return new CallableValue(definition);
    }
}
