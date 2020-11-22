package com.levkopo.vs.test;

import com.github.bloodshura.ignitium.activity.logging.XLogger;
import com.github.bloodshura.ignitium.activity.scanning.XScanner;
import com.github.bloodshura.ignitium.collection.view.XView;
import com.github.bloodshura.ignitium.io.Directory;
import com.github.bloodshura.ignitium.io.File;
import com.github.bloodshura.ignitium.worker.ParseWorker;
import com.levkopo.vs.compiler.BytecodeCompiler;

import static com.github.bloodshura.ignitium.sys.XSystem.millis;

public class BytecodeInteractiveTester {
	public static final Directory DIRECTORY = new Directory("./examples/bytecode");

	public static void main(String[] args) throws Exception {
		XView<File> files = DIRECTORY.getDeepFiles();
		int i = 0;

		for (File file : files) {
			XLogger.println(i++ + ". " + file.getRelativePath(DIRECTORY));
		}

		XLogger.print("> ");

		int option = -1;

		while (option < 0 || option >= files.size()) {
			String optionStr = XScanner.scan();

			if (ParseWorker.isInt(optionStr)) {
				option = ParseWorker.toInt(optionStr);
			}
		}

		File file = files.get(option);
		BytecodeCompiler bytecodeCompiler = new BytecodeCompiler();
		long start = millis();

		try {
			System.out.println(bytecodeCompiler.findClassesInFile(file.toPath().toString()));
		} catch (Exception e){
			e.printStackTrace();
		}

		long duration = millis() - start;

		XLogger.println("Duration: " + duration + "ms");
		System.exit(0);
	}
}
