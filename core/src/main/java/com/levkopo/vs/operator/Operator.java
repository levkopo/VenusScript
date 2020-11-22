package com.levkopo.vs.operator;

import com.github.bloodshura.ignitium.collection.view.XView;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public interface Operator {
	XView<String> getIdentifiers();
}