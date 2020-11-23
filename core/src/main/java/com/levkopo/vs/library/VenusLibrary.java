package com.levkopo.vs.library;

import com.levkopo.vs.function.Function;

import java.util.ArrayList;

public class VenusLibrary extends ArrayList<Function> {

	public boolean add(Class<? extends Function> object) {
		try {
			return add(object.newInstance());
		} catch (IllegalAccessException | InstantiationException exception) {
			throw new IllegalArgumentException("Could not instantiate method class \"" + object.getName() + "\"");
		}
	}

	@SafeVarargs
	public final boolean addAll(Class<? extends Function>... objects) {
		boolean allAdded = true;

		for (Class<? extends Function> object : objects) {
			if (!add(object)) {
				allAdded = false;
			}
		}

		return allAdded;
	}
}
