package com.levkopo.vs.library.dynamic;

import com.levkopo.vs.library.VenusLibrary;

public class DynamicLibrary extends VenusLibrary {
	public DynamicLibrary() {
		addAll(DynamicInclude.class, DynamicUsing.class);
	}
}