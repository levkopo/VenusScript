package com.levkopo.vs.library.system;

import com.levkopo.vs.library.VSLibrary;

public class SystemLibrary extends VSLibrary {
	public SystemLibrary() {
		addAllFunctions(GetEnvVar.class, GetProperty.class);
	}
}