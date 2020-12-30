package com.levkopo.vs.library.engine;

import com.levkopo.vs.library.VSLibrary;

public class EngineLibrary extends VSLibrary {
	public EngineLibrary() {
		addAllFunctions(Evaluate.class, HasFunction.class, Interpret.class, Run.class);
	}
}