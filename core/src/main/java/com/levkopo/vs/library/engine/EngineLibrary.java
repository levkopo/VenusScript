package com.levkopo.vs.library.engine;

import com.levkopo.vs.library.VenusLibrary;

public class EngineLibrary extends VenusLibrary {
	public EngineLibrary() {
		addAll(Evaluate.class, HasFunction.class, Interpret.class, Run.class);
	}
}