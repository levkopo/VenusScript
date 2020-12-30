package com.levkopo.vs.component;

import com.levkopo.vs.value.Value;

import java.util.List;

public class Annotation {
    public final String name;
    public final List<Value> data;

    public Annotation(String name, List<Value> data) {
        this.name = name;
        this.data = data;
    }
}
