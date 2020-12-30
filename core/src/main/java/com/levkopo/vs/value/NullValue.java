package com.levkopo.vs.value;

import com.levkopo.vs.type.PrimitiveType;

public class NullValue extends Value {

    public NullValue() {
        super(PrimitiveType.ANY);
    }

    @Override
    public Value clone() {
        return null;
    }

    @Override
    public Object value() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
