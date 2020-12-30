package com.github.levkoposc.expressions;

import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.expression.Expression;
import org.objectweb.asm.MethodVisitor;

public interface BytecodeCompiler{

    MethodVisitor compile(MethodVisitor visitor, Expression expression) throws ScriptCompileException;
}
