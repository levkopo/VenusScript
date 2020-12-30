package com.github.levkoposc;

import com.levkopo.vs.type.PrimitiveType;
import com.levkopo.vs.type.Type;

import java.util.List;
import java.util.function.Supplier;

public class TypeBuilder {
    private final List<Type> types;

    public TypeBuilder(List<Type> types) {
        this.types = types;
    }

    public static String build(Type type){
        if(type==PrimitiveType.STRING)
            return "Ljava/lang/String";
        else if(type==PrimitiveType.INTEGER)
            return "I";
        else if(type==PrimitiveType.BOOLEAN)
            return "I";
        else if(type==PrimitiveType.ARRAY)
            return "[Ljava/lang/Object";
        else if(type==PrimitiveType.DECIMAL)
            return "D";
        else if(type==PrimitiveType.VOID)
            return "V";
        else if(type==PrimitiveType.FUNCTION_REFERENCE){
            return "Ljava/util/function/Supplier";
        }

        return "Ljava/lang/Object";
    }

    public String build(){
        StringBuilder builder = new StringBuilder();
        for(Type type: types){
            builder.append(build(type)).append(";");
        }

        return builder.toString();
    }
}
