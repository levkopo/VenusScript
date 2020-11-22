package com.levkopo.vs.function.annotation;

import com.levkopo.vs.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MethodArgs {
	Class<? extends Value>[] value();
}