package com.levkopo.vs.library.lang.string;

import com.levkopo.vs.component.object.Attribute;
import com.levkopo.vs.exception.runtime.UndefinedVariableException;
import com.levkopo.vs.expression.Constant;
import com.levkopo.vs.library.ObjectClass;
import com.levkopo.vs.value.NullValue;

public class StringObject extends ObjectClass {

    public StringObject() {
        super("StringUtils", new Attribute("value", new Constant(new NullValue())));
        addFunction(new ToString());
        addFunction(new CharAt());
        addFunction(new Length());
        addFunction(new Trim());
        addFunction(new Substring());
    }

    @Override
    public String toString() {
        try {
            return getContext().getVar("value").getValue().value().toString();
        } catch (UndefinedVariableException ignored) {}

        return null;
    }
}
