package com.github.levkoposc.expressions;

import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.expression.Attribution;
import com.levkopo.vs.expression.Expression;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AttributionCompiler implements BytecodeCompiler {

    public final ContainerCompiler containerCompiler;

    public AttributionCompiler(ContainerCompiler containerCompiler) {
        this.containerCompiler = containerCompiler;
    }

    @Override
    public MethodVisitor compile(MethodVisitor visitor, Expression expression) throws ScriptCompileException {
        Attribution attribution = (Attribution) expression;
        visitor = new ExpressionCompiler(containerCompiler)
                .compile(visitor, attribution.getExpression());
        visitor.visitVarInsn(Opcodes.ASTORE,
                containerCompiler.getANumber(attribution.getName()));

        return visitor;
    }
}
