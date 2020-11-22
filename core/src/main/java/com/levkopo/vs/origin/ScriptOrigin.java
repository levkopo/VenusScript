package com.levkopo.vs.origin;

import com.github.bloodshura.ignitium.resource.PathResource;
import com.levkopo.vs.compiler.VenusLexer;
import com.levkopo.vs.compiler.VenusParser;
import com.levkopo.vs.component.Script;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.executor.ApplicationContext;

import java.io.IOException;

public interface ScriptOrigin {
	default Script compile(ApplicationContext applicationContext) throws ScriptCompileException {
		VenusLexer lexer;

		try {
			lexer = new VenusLexer(this);
		} catch (IOException exception) {
			throw new ScriptCompileException("Could not read script \"" + getScriptName() + "\": " + exception.getClass().getSimpleName() + ": " + exception.getMessage());
		}

		Script script = new Script(applicationContext, this);
		VenusParser parser = script.getParser();

		parser.parse(lexer, script);

		return script;
	}

	default ScriptOrigin findRelative(String includePath) {
		PathResource resource = new PathResource(includePath);

		return resource.exists() ? new StreamScriptOrigin(includePath, resource) : null;
	}

	String getScriptName();

	String read() throws IOException;
}
