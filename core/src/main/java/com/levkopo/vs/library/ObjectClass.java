package com.levkopo.vs.library;

import com.levkopo.vs.component.object.Attribute;
import com.levkopo.vs.component.object.ObjectDefinition;


public class ObjectClass extends ObjectDefinition {

    public ObjectClass(String name, Attribute... attrs) {
        super(name);
        for(Attribute attr: attrs)
            getAttributes().add(attr);
    }
}
