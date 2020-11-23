package com.levkopo.vs.library;

import com.levkopo.vs.function.Function;
import com.levkopo.vs.type.Type;

import java.util.ArrayList;
import java.util.List;

public class LibraryList extends ArrayList<VenusLibrary> {
	public Function findFunction(String name, List<Type> argumentTypes) {
		Function found = null;
		Function foundVarArgs = null;

		for (VenusLibrary library : this) {
			for (Function function : library) {
				if (function.accepts(name, argumentTypes)) {
					if (function.isVarArgs()) {
						if (foundVarArgs == null) {
							foundVarArgs = function;
						}
					} else {
						found = function;
					}
				}
			}
		}

		return found != null ? found : foundVarArgs;
	}

	/*public Function findObject(String name, XView<Type> argumentTypes) {
		XApi.requireNonNull(name, "name");

		Function found = null;
		Function foundVarArgs = null;

		for (VSLibrary library : this) {
			for (ObjectDefinition object: library.objects) {
				if (object.accepts(name, argumentTypes)) {
					if (function.isVarArgs()) {
						if (foundVarArgs == null) {
							foundVarArgs = function;
						}
					} else {
						found = function;
					}
				}
			}
		}

		return found != null ? found : foundVarArgs;
	}*/
}
