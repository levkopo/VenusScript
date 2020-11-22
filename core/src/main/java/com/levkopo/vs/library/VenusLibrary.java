package com.levkopo.vs.library;

import com.github.bloodshura.ignitium.collection.list.impl.XArrayList;
import com.levkopo.vs.component.object.ObjectDefinition;
import com.levkopo.vs.function.Function;

public class VenusLibrary extends XArrayList<Function> {

	@Deprecated
	public boolean add(Class<? extends Function> object) {
		try {
			return add(object.newInstance());
		} catch (IllegalAccessException | InstantiationException exception) {
			throw new IllegalArgumentException("Could not instantiate method class \"" + object.getName() + "\"");
		}
	}

	@Deprecated
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
