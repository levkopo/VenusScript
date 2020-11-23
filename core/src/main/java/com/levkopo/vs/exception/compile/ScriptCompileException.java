package com.levkopo.vs.exception.compile;


public class ScriptCompileException extends Exception {
	public ScriptCompileException(CharSequence message) {
		super((String) message);
	}

	public ScriptCompileException(String scriptName, int currentLine, CharSequence message) {
		this(message + " at line " + (currentLine + 1) + " of script \"" + scriptName + "\"");
	}
}