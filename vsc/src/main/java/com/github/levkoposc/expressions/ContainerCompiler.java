package com.github.levkoposc.expressions;

import com.levkopo.vs.component.Component;
import com.levkopo.vs.component.Container;
import com.levkopo.vs.component.SimpleComponent;
import com.levkopo.vs.exception.compile.ScriptCompileException;
import com.levkopo.vs.expression.Expression;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;

public class ContainerCompiler{

    public ScriptCompiler compiler;

    public final List<String> a = new ArrayList<>();

    public MethodVisitor compile(MethodVisitor visitor, Container container) throws ScriptCompileException {
        for(Component child: container.getChildren()){
            visitor.visitLineNumber(child.getSourceLine(), new Label());
            if(child instanceof SimpleComponent){
                Expression childExpression = ((SimpleComponent) child).getExpression();
                visitor = new ExpressionCompiler(this)
                        .compile(visitor, childExpression);
            }
        }

        return visitor;
    }

    public int getANumber(String name){
        if(!a.contains(name)){
            a.add(name);
            return a.size()-1;
        }

        return a.indexOf(name);
    }
}
