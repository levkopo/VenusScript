package com.levkopo.vs.library.lang.number;

import com.levkopo.vs.component.object.Attribute;
import com.levkopo.vs.expression.Constant;
import com.levkopo.vs.library.ObjectClass;
import com.levkopo.vs.value.NullValue;

public class NumberClass extends ObjectClass {

    public NumberClass() {
        super("Number", new Attribute("value", new Constant(new NullValue())));
        addFunction(new ToInteger());
        addFunction(new ToDecimal());
    }
}
