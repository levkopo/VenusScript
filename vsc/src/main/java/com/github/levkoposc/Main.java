package com.github.levkoposc;

import com.github.levkoposc.expressions.ContainerCompiler;
import com.github.levkoposc.expressions.ScriptCompiler;
import com.levkopo.vs.component.Script;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.executor.ApplicationContext;
import com.levkopo.vs.origin.FileScriptOrigin;
import org.apache.commons.io.FileUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Main {

    public static String sourcePath;

    public static void main(String[] args) throws ScriptCompileException, IOException {
        Map<String, String> compileArgs = new LinkedHashMap<>();

        String name = null;
        for(String arg: args){
            if(arg.startsWith("-")){
                name = arg.substring(1);
            }else compileArgs.put(name, arg);
        }

        sourcePath = compileArgs.getOrDefault("sourcepath", "./");

        System.out.println("Searching classes..");
        Map<String, Object> classes = getClasses(new File(sourcePath));

        System.out.println("Compile classes..");
        Map<String, Object> compiledClasses = compile(classes, "");

        System.out.println("Saving classes..");
        File outDir = new File(compileArgs.getOrDefault("out", "./out"));

        for (File myFile : outDir.listFiles())
            myFile.delete();

        save(outDir, compiledClasses);
    }

    private static void save(File file, Map<String, Object> classes) throws IOException {
        if(!file.isDirectory()&&!file.mkdir())
            return;

        for(Map.Entry<String, Object> entry: classes.entrySet()){
            if(entry.getValue() instanceof Map){
                save(new File(file.getAbsolutePath()+"/"+entry.getKey()),
                        (Map) entry.getValue());
            }else{
                FileUtils.writeByteArrayToFile(new File(file.getAbsolutePath()+"/"+
                                entry.getKey()+".class"),
                        (byte[]) entry.getValue());
            }
        }
    }

    private static Map<String, Object> compile(Map<String, Object> classes, String package_) throws ScriptCompileException {
        Map<String, Object> compiledClasses = new HashMap<>();
        for(Map.Entry<String, Object> entry: classes.entrySet()){
            String s = !package_.isEmpty() ? package_ + "/" : "";
            if(entry.getValue() instanceof Map){
                compiledClasses.put(entry.getKey(), compile((Map) entry.getValue(),
                        s +entry.getKey()));
            }else{
                FileScriptOrigin origin = new FileScriptOrigin((File) entry.getValue());
                Script script = origin.compile(new ApplicationContext());

                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,
                        s +
                                entry.getKey().substring(0, entry.getKey().indexOf('.')),
                        null, "java/lang/Object", null);

                cw = new ScriptCompiler(cw ,script, s).getClassVisitor();

                compiledClasses.put(entry.getKey().substring(0, entry.getKey().indexOf('.')),
                        cw.toByteArray());
            }
        }

        return compiledClasses;
    }

    private static Map<String, Object> getClasses(File files) throws ScriptCompileException {
        Map<String, Object> classes = new HashMap<>();
        if(files.listFiles()==null)
            throw new ScriptCompileException("Invalid path: "+files.getPath());

        for(File file: Objects.requireNonNull(files.listFiles())){
            if(file.isDirectory()){
                classes.put(file.getName(), getClasses(file));
            }else if(file.getName().endsWith(".vs")){
                classes.put(file.getName(), file);
            }
        }

        return classes;
    }
}
