package com.levkopo.vs.library;

import com.levkopo.vs.component.object.ObjectDefinition;
import com.levkopo.vs.function.Function;
import com.levkopo.vs.library.lang.string.StringObject;

import java.util.ArrayList;

public class VSLibrary{

    public final ArrayList<Function> functions = new ArrayList<>();
    public final ArrayList<ObjectDefinition> objects = new ArrayList<>();

    public boolean addFunction(Class<? extends Function> object) {
        try {
            return functions.add(object.newInstance());
        } catch (IllegalAccessException | InstantiationException exception) {
            throw new IllegalArgumentException("Could not instantiate method class \"" + object.getName() + "\"");
        }
    }

    @SafeVarargs
    public final boolean addAllFunctions(Class<? extends Function>... objects) {
        boolean attach = true;
        for(Class<? extends Function> object: objects) {
            try {
                if(!functions.add(object.newInstance()))
                    attach = false;

            } catch (IllegalAccessException | InstantiationException exception) {
                throw new IllegalArgumentException("Could not instantiate method class \"" + object.getName() + "\"");
            }
        }

        return attach;
    }

    public boolean addFunction(Function func) {
        return functions.add(func);
    }

    public boolean addObject(Class<? extends ObjectDefinition> object) {
        try {
            return objects.add(object.newInstance());
        } catch (IllegalAccessException | InstantiationException exception) {
            throw new IllegalArgumentException("Could not instantiate method class \"" + object.getName() + "\"");
        }
    }
}
