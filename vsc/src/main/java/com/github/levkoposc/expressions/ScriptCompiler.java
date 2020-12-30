package com.github.levkoposc.expressions;

import com.github.levkoposc.TypeBuilder;
import com.levkopo.vs.component.Script;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.function.Definition;
import com.levkopo.vs.function.Function;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ScriptCompiler extends ContainerCompiler {

    public final Script script;
    public final String classpath;
    private final ClassWriter visitor;

    public ScriptCompiler(ClassWriter visitor, Script script, String classpath) throws ScriptCompileException {
        this.classpath = classpath;
        this.script = script;
        this.visitor = visitor;
        compiler = this;
        getANumber("params");

        MethodVisitor methodVisitor = visitor.visitMethod(
                Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC,
                "main", "([Ljava/lang/String;)V",
                null, null);
        methodVisitor.visitCode();

        super.compile(methodVisitor, script);

        methodVisitor.visitMaxs(1, a.size());
        methodVisitor.visitEnd();
        visitor.visitEnd();

        for(Function function: script.getFunctions()){
            if(function instanceof Definition)
                visitor = parseDefinition(visitor, (Definition) function);
        }
    }

    private ClassWriter parseDefinition(ClassWriter visitor, Definition definition) throws ScriptCompileException {
        String types = new TypeBuilder(definition.getArgumentTypes()).build();
        MethodVisitor methodVisitor = visitor.visitMethod(
                Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC,
                definition.getName(), "("+types+")"+TypeBuilder.build(definition.getReturnType()),
                null, null);
        methodVisitor.visitCode();

        ContainerCompiler containerCompiler = new ContainerCompiler();
        containerCompiler.compiler = this;
        //methodVisitor = containerCompiler.compile(methodVisitor, definition);

        methodVisitor.visitMaxs(1, containerCompiler.a.size());
        methodVisitor.visitEnd();
        visitor.visitEnd();

        return visitor;
    }


    public ClassWriter getClassVisitor(){
        return visitor;
    }
}
