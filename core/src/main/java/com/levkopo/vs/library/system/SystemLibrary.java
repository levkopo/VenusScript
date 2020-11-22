package com.levkopo.vs.library.system;

import com.levkopo.vs.library.VenusLibrary;

public class SystemLibrary extends VenusLibrary {
	public SystemLibrary() {
		addAll(GetEnvVar.class, GetProperty.class);
	}
}