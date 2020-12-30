package com.levkopo.vs.library.request;

import com.levkopo.vs.component.object.Attribute;
import com.levkopo.vs.expression.Constant;
import com.levkopo.vs.library.ObjectClass;
import com.levkopo.vs.value.MapValue;
import com.levkopo.vs.value.NullValue;
import com.levkopo.vs.value.StringValue;

import java.util.HashMap;

public class RequestObject extends ObjectClass {

    public RequestObject() {
        super("request", new Attribute("url", new Constant(new NullValue())),
                new Attribute("method", new Constant(new StringValue("GET"))),
                new Attribute("headers", new Constant(new MapValue(new HashMap<>()))),
                new Attribute("parameters", new Constant(new MapValue(new HashMap<>())))
        );
        addFunction(new SetParameters());
        addFunction(new Request());
        addFunction(new SetHeader());
    }
}
