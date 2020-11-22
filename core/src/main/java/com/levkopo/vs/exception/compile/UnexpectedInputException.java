package com.levkopo.vs.exception.compile;

public class UnexpectedInputException extends ScriptCompileException {
	public UnexpectedInputException(String scriptName, int currentLine, String message) {
		super(scriptName, currentLine, message);
	}
}