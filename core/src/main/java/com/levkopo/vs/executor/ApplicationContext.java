package com.levkopo.vs.executor;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

public class ApplicationContext extends Context {
	private int currentLine;
	private VenusExecutor executor;
	private final Map<String, Supplier<VenusLibrary>> librarySuppliers;
	private final Map<String, Object> userData;

	public ApplicationContext() {
		super(new SimpleContainer("APPLICATION"), null);
		this.librarySuppliers = new HashMap<>();
		this.userData = new HashMap<>();

		getLibrarySuppliers().put("dynamic", DynamicLibrary::new);
		getLibrarySuppliers().put("engine", EngineLibrary::new);
		getLibrarySuppliers().put("math", MathLibrary::new);
		getLibrarySuppliers().put("std", StdLibrary::new);
		getLibrarySuppliers().put("system", SystemLibrary::new);
		getLibrarySuppliers().put("time", TimeLibrary::new);
		getLibrarySuppliers().put("json", JSONLibrary::new);
		getLibrarySuppliers().put("request", RequestLibrary::new);
		setUserData("in", new Scanner(System.in));
		setUserData("out", (OutputReference) System.out::println);
		setUserData("version", Config.version);
	}

	@Override
	public ApplicationContext clone() {
		ApplicationContext context = new ApplicationContext();

		context.getLibrarySuppliers().putAll(getLibrarySuppliers());
		context.userData.putAll(userData);
		context.setCurrentLine(currentLine());

		return context;
	}

	public VenusExecutor currentExecutor() {
		return executor;
	}

	public int currentLine() {
		return currentLine;
	}

	public Map<String, Supplier<VenusLibrary>> getLibrarySuppliers() {
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
		userData.put(name, value);
	}

	void setCurrentLine(int currentLine) {
		this.currentLine = currentLine;
	}

	void setExecutor(VenusExecutor executor) {
		this.executor = executor;
	}
}
