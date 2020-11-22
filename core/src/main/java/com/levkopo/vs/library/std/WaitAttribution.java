package com.levkopo.vs.library.std;

import com.github.bloodshura.ignitium.activity.logging.XLogger;
import com.github.bloodshura.ignitium.collection.list.XList;
import com.github.bloodshura.ignitium.collection.list.impl.XArrayList;
import com.levkopo.vs.exception.runtime.InvalidFunctionParameterException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.expression.BinaryOperation;
import com.levkopo.vs.expression.Expression;
import com.levkopo.vs.expression.FunctionCall;
import com.levkopo.vs.expression.UnaryOperation;
import com.levkopo.vs.expression.Variable;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.Value;

@MethodArgs(Value.class)
@MethodName("wait")
public class WaitAttribution extends VoidMethod {
	@Override
	public void callVoid(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		Expression expression = descriptor.getExpressions().get(0);
		XList<Variable> list = new XArrayList<>();
		Object lock = new Object();

		scan(context, expression, list);
		list.forEachExceptional(variable -> context.getVar(variable).addChangeMonitor(lock));

		Value value = expression.resolve(context); // Maybe value changed after it was resolved.

		while (!(value instanceof BoolValue && ((BoolValue) value).value())) {
			try {
				synchronized (lock) {
					lock.wait();
				}
			} catch (InterruptedException exception) {
				XLogger.warnln("Thread " + Thread.currentThread() + " interrupted while 'wait' was locking.");

				break;
			}

			value = expression.resolve(context);
		}

		list.forEachExceptional(variable -> context.getVar(variable).removeChangeMonitor(lock));
	}

	private void scan(Context context, Expression expression, XList<Variable> list) throws ScriptRuntimeException {
		if (expression instanceof BinaryOperation) {
			BinaryOperation operation = (BinaryOperation) expression;

			scan(context, operation.getLeft(), list);
			scan(context, operation.getRight(), list);
		} else if (expression instanceof FunctionCall) {
			throw new InvalidFunctionParameterException(context, "Cannot embed a function call on arguments for 'wait' method");
		} else if (expression instanceof UnaryOperation) {
			UnaryOperation operation = (UnaryOperation) expression;

			scan(context, operation.getExpression(), list);
		} else if (expression instanceof Variable) {
			list.add((Variable) expression);
		}
	}
}
