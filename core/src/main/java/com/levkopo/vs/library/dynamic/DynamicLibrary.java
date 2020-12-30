package com.levkopo.vs.library.dynamic;

import com.levkopo.vs.library.VSLibrary;

public class DynamicLibrary extends VSLibrary {
	public DynamicLibrary() {
		addAllFunctions(DynamicInclude.class, DynamicUsing.class);
	}
}