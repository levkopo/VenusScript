package com.levkopo.vs.test;

public class BytecodeInteractiveTester {
	/*ublic static final Directory DIRECTORY = new Directory("./examples/bytecode");

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
	}*/
}
