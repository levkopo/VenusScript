package com.levkopo.vs.value;

import com.levkopo.vs.type.PrimitiveType;
import com.levkopo.vs.type.Type;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class NullValue extends Value {

    public NullValue() {
        super(PrimitiveType.ANY);
    }

    @Override
    public Value clone() {
        return null;
    }

    @Override
    public Object value() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public MethodVisitor visit(MethodVisitor visitor) {
        visitor.visitLdcInsn(Opcodes.ACONST_NULL);
        return visitor;
    }
}
