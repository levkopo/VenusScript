package com.levkopo.vs;

import com.levkopo.vs.type.PrimitiveType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public static final int CODE = 2;
    public static final String VERSION = "0.2";

    public static Map<String, Object> values() {
        Map<String, Object> output = new HashMap<>();

        for (Field f : Config.class.getDeclaredFields())
            if (Modifier.isStatic(f.getModifiers()))
                try {
                    output.put(f.getName(), f.get(PrimitiveType.class));
                } catch (IllegalAccessException ignored) {}

        return output;
    }
}
