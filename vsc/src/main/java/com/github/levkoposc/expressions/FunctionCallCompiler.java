package com.github.levkoposc.expressions;

import com.github.levkoposc.TypeBuilder;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.expression.Expression;
import com.levkopo.vs.expression.FunctionCall;
import com.levkopo.vs.function.Definition;
import com.levkopo.vs.function.Function;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

public class FunctionCallCompiler implements BytecodeCompiler {

    private final ScriptCompiler compiler;
    private final ContainerCompiler containerCompiler;

    public FunctionCallCompiler(ContainerCompiler containerCompiler) {
        this.containerCompiler = containerCompiler;
        this.compiler = containerCompiler.compiler;
    }

    @Override
    public MethodVisitor compile(MethodVisitor visitor, Expression expression) throws ScriptCompileException {
        FunctionCall functionCall = (FunctionCall) expression;
        for(Expression arg: functionCall.getArguments()){
            visitor = new ExpressionCompiler(containerCompiler)
                    .compile(visitor, arg);
        }

        Definition function = (Definition) findLocalFunction(functionCall);
        if(function==null)
            throw new ScriptCompileException("Function "+functionCall.getFunctionName()+" not found");

        String types = new TypeBuilder(function.getArgumentTypes()).build();
        visitor.visitMethodInsn(INVOKESTATIC, compiler.classpath,
                functionCall.getFunctionName(), "("+types+")"+TypeBuilder.build(function.getReturnType()), false);

        return visitor;
    }

    private Function findLocalFunction(FunctionCall functionCall){
        for(Function function: compiler.script.getFunctions()){
            if(function.getName().equals(functionCall.getFunctionName())){
                return function;
            }
        }

        return null;
    }
}
