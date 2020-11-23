package com.levkopo.vs.compiler;

import com.levkopo.vs.BytecodeContext;
import com.levkopo.vs.component.Component;
import com.levkopo.vs.component.Script;
import com.levkopo.vs.component.object.ObjectDefinition;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.function.Definition;
import com.levkopo.vs.origin.FileScriptOrigin;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BytecodeCompiler {

    public List<ObjectDefinition> findClassesInFile(String path) throws ScriptCompileException {
        String filename = path;
        if(filename.lastIndexOf("\\")!=-1)
            filename = filename.substring(filename.lastIndexOf("\\")+1);

        FileScriptOrigin origin = new FileScriptOrigin(new File(path));
        Script script = origin.compile(new BytecodeContext());
        ObjectDefinition topLevelClass = new ObjectDefinition(filename.substring(0, filename.indexOf(".")));
        List<ObjectDefinition> classes = new ArrayList<>();

        for(Component c : script.getChildren()){
            if(c instanceof ObjectDefinition){
                classes.add((ObjectDefinition) c);
            }else if(c instanceof Definition){
                topLevelClass.getChildren().add(c);
            }
        }

        if(topLevelClass.getChildren().size()!=0)
            classes.add(topLevelClass);

        return classes;
    }

    public byte[] compile(ObjectDefinition objectDefinition){
        ClassWriter classVisitor = new ClassWriter(Opcodes.V1_8);
        classVisitor.visit(Opcodes.V1_8,
                Opcodes.ACC_PUBLIC, objectDefinition.getName(), null, null, null);

        for(Component c : objectDefinition.getChildren()){
            if(c instanceof Definition){
                Definition definition = (Definition) c;
            }
        }

        return classVisitor.toByteArray();
    }
}
