package com.levkopo.vs.library.json;

import com.levkopo.vs.library.VSLibrary;

public class JSONLibrary extends VSLibrary {

	public JSONLibrary(){
		addAllFunctions(JSONDecode.class, JSONEncode.class);
	}
}
