package com.github.bloodshura.ignitium.venus.library.json;

import com.github.bloodshura.ignitium.venus.library.VenusLibrary;

public class JSONLibrary extends VenusLibrary {

	public JSONLibrary(){
		addAll(JSONDecode.class, JSONEncode.class);
	}
}
