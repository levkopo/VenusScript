package com.levkopo.vs.library.math;

import com.levkopo.vs.library.VSLibrary;

import java.lang.reflect.Method;

public class MathLibrary extends VSLibrary {
	public MathLibrary() {
		for (Method method : Math.class.getDeclaredMethods()) {
			if (MathFunction.validate(method)) {
				addFunction(new MathFunction(method));
			}
		}

		addFunction(RandDecimal.class);
		addFunction(RandInt.class);
	}
}