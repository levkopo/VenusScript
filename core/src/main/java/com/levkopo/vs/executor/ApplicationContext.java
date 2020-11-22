package com.levkopo.vs.executor;

import com.github.bloodshura.ignitium.activity.logging.XLogger;
import com.github.bloodshura.ignitium.collection.map.XMap;
import com.github.bloodshura.ignitium.collection.map.impl.XLinkedMap;
import com.github.bloodshura.ignitium.lang.annotation.Internal;
import com.levkopo.vs.Config;
import com.levkopo.vs.component.SimpleContainer;
import com.levkopo.vs.exception.runtime.UndefinedVariableException;
import com.levkopo.vs.library.VenusLibrary;
import com.levkopo.vs.library.dynamic.DynamicLibrary;
import com.levkopo.vs.library.engine.EngineLibrary;
import com.levkopo.vs.library.json.JSONLibrary;
import com.levkopo.vs.library.math.MathLibrary;
import com.levkopo.vs.library.request.RequestLibrary;
import com.levkopo.vs.library.std.StdLibrary;
import com.levkopo.vs.library.system.SystemLibrary;
import com.levkopo.vs.library.time.TimeLibrary;

import java.util.function.Supplier;

public class ApplicationContext extends Context {
	private int currentLine;
	private VenusExecutor executor;
	private final XMap<String, Supplier<VenusLibrary>> librarySuppliers;
	private final XMap<String, Object> userData;

	public ApplicationContext() {
		super(new SimpleContainer("APPLICATION"), null);
		this.librarySuppliers = new XLinkedMap<>();
		this.userData = new XLinkedMap<>();

		getLibrarySuppliers().add("dynamic", DynamicLibrary::new);
		getLibrarySuppliers().add("engine", EngineLibrary::new);
		getLibrarySuppliers().add("math", MathLibrary::new);
		getLibrarySuppliers().add("std", StdLibrary::new);
		getLibrarySuppliers().add("system", SystemLibrary::new);
		getLibrarySuppliers().add("time", TimeLibrary::new);
		getLibrarySuppliers().add("json", JSONLibrary::new);
		getLibrarySuppliers().add("request", RequestLibrary::new);
		setUserData("in", XLogger.DEFAULT);
		setUserData("out", (OutputReference) XLogger::print);
		setUserData("version", Config.version);
	}

	@Override
	public ApplicationContext clone() {
		ApplicationContext context = new ApplicationContext();

		context.getLibrarySuppliers().addAll(getLibrarySuppliers());
		context.userData.addAll(userData);
		context.setCurrentLine(currentLine());

		return context;
	}

	public VenusExecutor currentExecutor() {
		return executor;
	}

	public int currentLine() {
		return currentLine;
	}

	public XMap<String, Supplier<VenusLibrary>> getLibrarySuppliers() {
		return librarySuppliers;
	}

	public <E> E getUserData(String name, Class<E> type) throws UndefinedVariableException {
		Object value = userData.get(name);

		if (value != null && type.isAssignableFrom(value.getClass())) {
			return (E) value;
		}

		throw new UndefinedVariableException(this, name);
	}

	public void setUserData(String name, Object value) {
		userData.set(name, value);
	}

	@Internal
	void setCurrentLine(int currentLine) {
		this.currentLine = currentLine;
	}

	@Internal
	void setExecutor(VenusExecutor executor) {
		this.executor = executor;
	}
}
