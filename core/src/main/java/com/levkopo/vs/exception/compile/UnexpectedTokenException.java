package com.levkopo.vs.exception.compile;

public class UnexpectedTokenException extends ScriptCompileException {
	public UnexpectedTokenException(String scriptName, int currentLine, String message) {
		super(scriptName, currentLine, message);
	}
}