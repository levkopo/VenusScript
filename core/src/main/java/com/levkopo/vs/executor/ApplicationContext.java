package com.levkopo.vs.executor;

import com.levkopo.vs.component.SimpleContainer;
import com.levkopo.vs.exception.runtime.UndefinedVariableException;
import com.levkopo.vs.library.StdLibrary;
import com.levkopo.vs.library.VSLibrary;
import com.levkopo.vs.library.dynamic.DynamicLibrary;
import com.levkopo.vs.library.engine.EngineLibrary;
import com.levkopo.vs.library.json.JSONLibrary;
import com.levkopo.vs.library.math.MathLibrary;
import com.levkopo.vs.library.request.RequestLibrary;
import com.levkopo.vs.library.system.SystemLibrary;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

public class ApplicationContext extends Context {
	private int currentLine;
	private VenusExecutor executor;
	private final Map<String, Supplier<VSLibrary>> librarySuppliers;
	private final Map<String, Object> userData;

	public ApplicationContext() {
		super(new SimpleContainer("APPLICATION"), null);
		this.librarySuppliers = new HashMap<>();
		this.userData = new HashMap<>();

		getLibrarySuppliers().put("dynamic", DynamicLibrary::new);
		getLibrarySuppliers().put("engine", EngineLibrary::new);
		getLibrarySuppliers().put("math", MathLibrary::new);
		getLibrarySuppliers().put("system", SystemLibrary::new);
		getLibrarySuppliers().put("json", JSONLibrary::new);
		getLibrarySuppliers().put("request", RequestLibrary::new);
		getLibrarySuppliers().put("std", StdLibrary::new);
		setUserData("timezone", ZoneId.systemDefault());
		setUserData("in", new Scanner(System.in));
		setUserData("err", (OutputReference) System.err::println);
		setUserData("out", (OutputReference) System.out::println);
	}

	@Override
	public ApplicationContext clone() throws CloneNotSupportedException {
		ApplicationContext context = (ApplicationContext) super.clone();

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

	public Map<String, Supplier<VSLibrary>> getLibrarySuppliers() {
		return librarySuppliers;
	}

	@SuppressWarnings("unchecked")
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

	public void setCurrentLine(int currentLine) {
		this.currentLine = currentLine;
	}

	public void setExecutor(VenusExecutor executor) {
		this.executor = executor;
	}
}
