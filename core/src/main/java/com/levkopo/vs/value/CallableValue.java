package com.levkopo.vs.value;

import com.levkopo.vs.function.Definition;
import com.levkopo.vs.type.PrimitiveType;

public class CallableValue extends Value{

    private final Definition definition;

    public CallableValue(Definition definition) {
        super(PrimitiveType.CALLABLE);
        this.definition = definition;
    }

    @Override
    public Value clone() {
        return null;
    }

    @Override
    public Definition value() {
        return definition;
    }
}
