package com.github.levkoposc.expressions;

import com.levkopo.vs.component.branch.Return;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.expression.*;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ExpressionCompiler implements BytecodeCompiler {

    public final ContainerCompiler containerCompiler;

    public ExpressionCompiler(ContainerCompiler containerCompiler) {
        this.containerCompiler = containerCompiler;
    }

    @Override
    public MethodVisitor compile(MethodVisitor visitor, Expression expression) throws ScriptCompileException {
        if(expression instanceof Attribution){
            Attribution attribution = (Attribution) expression;
            return new AttributionCompiler(containerCompiler)
                    .compile(visitor, attribution);
        }else if(expression instanceof Constant){
            Constant constant = (Constant) expression;
            visitor.visitLdcInsn(constant.getValue().value());
            return visitor;
        }else if(expression instanceof FunctionCall){
            return new FunctionCallCompiler(containerCompiler)
                    .compile(visitor, expression);
        }else if(expression instanceof Variable){
            Variable variable = (Variable) expression;
            visitor.visitVarInsn(Opcodes.ALOAD, containerCompiler.getANumber(variable.getName()));
            return visitor;
        }

        return visitor;
    }
}
