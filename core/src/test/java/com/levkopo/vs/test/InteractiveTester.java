package com.levkopo.vs.test;

import com.levkopo.vs.component.Script;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.executor.ApplicationContext;
import com.levkopo.vs.executor.OutputReference;
import com.levkopo.vs.executor.VenusExecutor;
import com.levkopo.vs.origin.FileScriptOrigin;
import com.levkopo.vs.origin.ScriptMode;
import com.levkopo.vs.origin.ScriptOrigin;
import com.levkopo.vs.origin.SimpleScriptOrigin;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class InteractiveTester {
	//public static final Directory DIRECTORY = new Directory("./examples");
	public static final boolean LIGHTWEIGHT_ERRORS = false;

	public static void main(String[] args) throws Exception {
		File[] dirs = new File("./tests").listFiles();
		int i = 0;

		for (File file : dirs) {
			System.out.println(i++ + ". " + file.getName());
		}

		System.out.print("> ");

		int option = -1;
		while (option < 0 || option >= dirs.length) {
			String optionStr = new Scanner(System.in).next();

			try{
				option = Integer.parseInt(optionStr);
			}catch (Exception ignored){}
		}

		StringBuilder output = new StringBuilder();
		ApplicationContext ctx = new ApplicationContext();
		ctx.setUserData("out", (OutputReference) output::append);

		int completed = 0;
		for(File file: Objects.requireNonNull(dirs[option].listFiles())){
			System.out.println();
			System.out.println("Test: "+file.getName());

			String content = new FileScriptOrigin(file).read();
			SimpleScriptOrigin origin = new SimpleScriptOrigin(file.getName(), content.split("--")[0]);
			Script script;

			if (LIGHTWEIGHT_ERRORS) {
				try {
					script = origin.compile(ctx);
				} catch (ScriptCompileException exception) {
					exception.printStackTrace();
					return;
				}
			} else {
				script = origin.compile(ctx);
			}

			VenusExecutor executor = new VenusExecutor();
			long start = System.currentTimeMillis();

			try {
				executor.run(script, ScriptMode.NORMAL);
			} catch (Exception e){
				e.printStackTrace();
			}

			long duration = System.currentTimeMillis() - start;
			if(!output.toString().trim().equals(content.split("--")[1].trim())){
				System.out.println("Fail! Output: \""+output.toString().trim()+"\"");
			}else{
				System.out.println("Done!");
				completed++;
			}

			output.setLength(0);
			System.out.println("Duration: " + duration + "ms");
			System.out.println();
		}

		System.out.println("Result: "+completed+"/"+Objects.requireNonNull(dirs[option].listFiles()).length);
	}
}
