package com.levkopo.vs.library.json;

import com.levkopo.vs.library.VenusLibrary;

public class JSONLibrary extends VenusLibrary {

	public JSONLibrary(){
		addAll(JSONDecode.class, JSONEncode.class);
	}
}
