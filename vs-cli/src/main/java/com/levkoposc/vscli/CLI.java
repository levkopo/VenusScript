package com.levkoposc.vscli;

import com.levkopo.vs.component.Script;
import com.levkopo.vs.executor.ApplicationContext;
import com.levkopo.vs.executor.VenusExecutor;
import com.levkopo.vs.origin.FileScriptOrigin;
import com.levkopo.vs.origin.ScriptMode;
import com.levkopo.vs.origin.ScriptOrigin;

import java.io.File;
import java.io.PrintStream;

public class CLI {

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        VenusExecutor executor = new VenusExecutor((throwable -> System.err.println(
                "Error: "+throwable.getClass().getSimpleName()+": "+throwable.getMessage()
        )));
        ScriptOrigin origin = new FileScriptOrigin(new File(args[0]));
        Script script = origin.compile(new ApplicationContext());
        executor.run(script, ScriptMode.NORMAL);
    }
}
