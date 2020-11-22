package com.levkopo.vs.test;

import com.github.bloodshura.ignitium.activity.logging.XLogger;
import com.github.bloodshura.ignitium.activity.scanning.XScanner;
import com.github.bloodshura.ignitium.collection.view.XView;
import com.github.bloodshura.ignitium.io.Directory;
import com.github.bloodshura.ignitium.io.File;
import com.github.bloodshura.ignitium.worker.ParseWorker;
import com.levkopo.vs.component.Component;
import com.levkopo.vs.component.Container;
import com.levkopo.vs.component.Script;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.ApplicationContext;
import com.levkopo.vs.executor.VenusExecutor;
import com.levkopo.vs.origin.FileScriptOrigin;
import com.levkopo.vs.origin.ScriptMode;
import com.levkopo.vs.origin.ScriptOrigin;

import static com.github.bloodshura.ignitium.sys.XSystem.*;

public class InteractiveTester {
	public static final Directory DIRECTORY = new Directory("./examples");
	public static final boolean LIGHTWEIGHT_ERRORS = false;

	public static void main(String[] args) throws Exception {
		XView<File> files = DIRECTORY.getDeepFiles();
		int i = 0;

		for (File file : files) {
			XLogger.println(i++ + ". " + file.getRelativePath(DIRECTORY));
		}

		XLogger.print("> ");

		int option = -1;
		boolean printAst = false;

		while (option < 0 || option >= files.size()) {
			String optionStr = XScanner.scan();

			if (optionStr.startsWith("*")) {
				optionStr = optionStr.substring(1);
				printAst = true;
			}

			if (ParseWorker.isInt(optionStr)) {
				option = ParseWorker.toInt(optionStr);
			}
		}

		File file = files.get(option);
		ScriptOrigin origin = new FileScriptOrigin(file);
		Script script;

		if (LIGHTWEIGHT_ERRORS) {
			try {
				script = origin.compile(new ApplicationContext());
			} catch (ScriptCompileException exception) {
				XLogger.warnln("COMPILE ERR: " + exception.getMessage());

				return;
			}
		} else {
			script = origin.compile(new ApplicationContext());
		}

		if (printAst) {
			print(script);
			XLogger.newLine();
		}

		VenusExecutor executor = new VenusExecutor();
		long start = millis();

		try {
			executor.run(script, ScriptMode.NORMAL);
		} catch (Exception e){
			e.printStackTrace();
		}

		long duration = millis() - start;

		XLogger.println("Duration: " + duration + "ms");
		System.exit(0);
	}

	private static void print(Component component) {
		XLogger.println(component);

		if (component instanceof Container) {
			XLogger.pushTab();

			for (Component child : ((Container) component).getChildren()) {
				print(child);
			}

			XLogger.popTab();
		}
	}
}
