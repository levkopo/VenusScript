package com.levkopo.vs.library;

import com.levkopo.vs.component.object.ObjectDefinition;
import com.levkopo.vs.function.Function;
import com.levkopo.vs.type.Type;

import java.util.ArrayList;
import java.util.List;

public class LibraryList extends ArrayList<VSLibrary> {

	public Function findFunction(String name, List<Type> argumentTypes) {
		Function found = null;
		Function foundVarArgs = null;

		for (VSLibrary library : this) {
			for (Function function : library.functions) {
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

	public ObjectDefinition findObject(String name) {
		for (VSLibrary library : this) {
			for (ObjectDefinition object: library.objects) {
				if (object.getName().equals(name)) {
					return object;
				}
			}
		}

		return null;
	}
}
