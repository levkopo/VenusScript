package com.levkopo.vs.component;

import com.levkopo.vs.compiler.VenusParser;
import com.levkopo.vs.component.object.ObjectDefinition;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.ApplicationContext;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.Function;
import com.levkopo.vs.library.LibraryList;
import com.levkopo.vs.library.lang.LangLibrary;
import com.levkopo.vs.origin.ScriptOrigin;
import com.levkopo.vs.type.Type;

import java.util.ArrayList;
import java.util.List;

public class Script extends Container {
	private final ApplicationContext appContext;
	private final List<Script> includes;
	private final LibraryList libraryList;
	private final ScriptOrigin origin;
	private final VenusParser parser;

	public Script(ApplicationContext appContext, ScriptOrigin origin) {
		this.appContext = appContext;
		this.context = new Context(this, null);
		this.includes = new ArrayList<>();
		this.libraryList = new LibraryList();
		this.origin = origin;
		this.parser = new VenusParser(this);

		this.libraryList.add(new LangLibrary());
	}

	@Override
	public Function findFunction(Context context, String name, List<Type> argumentTypes) throws ScriptRuntimeException {
		try {
			return super.findFunction(context, name, argumentTypes);
		} catch (ScriptRuntimeException exception) {
			for (Script script : getIncludes()) {
				try {
					return script.findFunction(context, name, argumentTypes);
				} catch (ScriptRuntimeException ignored) {}
			}

			Function function = getLibraryList().findFunction(name, argumentTypes);

			if (function != null) {
				return function;
			}

			throw exception;
		}
	}

	@Override
	public ObjectDefinition findObjectDefinition(Context context, String name) throws ScriptRuntimeException {
		try {
			return super.findObjectDefinition(context, name);
		}catch (ScriptRuntimeException exception) {
			for (Script script : getIncludes()) {
				try {
					return script.findObjectDefinition(context, name);
				} catch (ScriptRuntimeException ignored) { }
			}

			ObjectDefinition object = getLibraryList().findObject(name);
			if (object != null) {
				return object;
			}

			throw exception;
		}
	}

	@Override
	public ApplicationContext getApplicationContext() {
		return appContext;
	}

	public String getDisplayName() {
		return getOrigin().getScriptName();
	}

	public List<Script> getIncludes() {
		return includes;
	}

	public LibraryList getLibraryList() {
		return libraryList;
	}

	public ScriptOrigin getOrigin() {
		return origin;
	}

	public VenusParser getParser() {
		return parser;
	}

	@Override
	public Script getScript() {
		return this;
	}

	public void include(String includePath, boolean maybe) throws ScriptCompileException {
		ScriptOrigin origin = getOrigin().findRelative(includePath);

		if (origin != null) {
			Script script = origin.compile(getApplicationContext());

			getIncludes().add(script);
		}else {
			throw new ScriptCompileException("Could not find script \"" + includePath + "\"");
		}
	}

	@Deprecated
	@Override
	public void setParent(Container parent) {
		throw new IllegalStateException("Cannot define a script's parent");
	}

	@Override
	public String toString() {
		return "script(name=" + getDisplayName() + ", origin=" + getOrigin() + ", includes=" + getIncludes() + ')';
	}
}
