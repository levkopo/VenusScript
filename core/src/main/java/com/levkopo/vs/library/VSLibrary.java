package com.levkopo.vs.library;

import com.github.bloodshura.ignitium.collection.list.impl.XArrayList;
import com.github.bloodshura.ignitium.collection.map.XMap;
import com.github.bloodshura.ignitium.collection.map.impl.XLinkedMap;
import com.levkopo.vs.component.object.ObjectDefinition;
import com.levkopo.vs.function.Function;

public class VSLibrary{

    public final XArrayList<Function> functions = new XArrayList<>();
    public final XArrayList<ObjectDefinition> objects = new XArrayList<>();

    public boolean addFunction(Class<? extends Function> object) {
        try {
            return functions.add(object.newInstance());
        } catch (IllegalAccessException | InstantiationException exception) {
            throw new IllegalArgumentException("Could not instantiate method class \"" + object.getName() + "\"");
        }
    }

    public boolean addObject(Class<? extends ObjectDefinition> object) {
        try {
            return objects.add(object.newInstance());
        } catch (IllegalAccessException | InstantiationException exception) {
            throw new IllegalArgumentException("Could not instantiate method class \"" + object.getName() + "\"");
        }
    }
}
