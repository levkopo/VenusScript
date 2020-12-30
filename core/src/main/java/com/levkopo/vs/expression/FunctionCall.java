package com.levkopo.vs.expression;

import com.levkopo.vs.exception.runtime.InvalidFunctionParameterException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.executor.OutputReference;
import com.levkopo.vs.function.Function;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.type.PrimitiveType;
import com.levkopo.vs.type.Type;
import com.levkopo.vs.value.CallableValue;
import com.levkopo.vs.value.DecimalValue;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FunctionCall implements Expression {
	private final Expression[] arguments;
	private final String functionName;

	public FunctionCall(String functionName, Expression... arguments) {
		this.arguments = arguments;
		this.functionName = functionName;
	}

	public List<Expression> getArguments() {
		return Arrays.asList(arguments);
	}

	public String getFunctionName() {
		return functionName;
	}

	@Override
	public Value resolve(Context context, Context args_parent) throws ScriptRuntimeException{
		List<Value> values = new ArrayList<>();
		List<Type> types = new ArrayList<>();
		for(Expression expression: getArguments()) {
			Value value = expression.resolve(context, args_parent);
			values.add(value);
			types.add(value.getType());
		}

		Function function = context.getOwner().findFunction(context, getFunctionName(), types);

		List<Value> list = new ArrayList<>();
		int i = 0;

		if(function.deprecated()){
			OutputReference reference = context.getApplicationContext().getUserData("err", OutputReference.class);
			reference.output("Function "+function.getName()+" deprecated!");
		}

		// This check is necessary because of function references being untyped (issue #9).
		if (!function.isVarArgs()
				&& types.size() != function.getArgumentTypes().size()) {
			throw new InvalidFunctionParameterException(context, "Function \"" + function + "\" expected " + function.getArgumentTypes().size() + " arguments; received " + types.size());
		}

		for (Value value : values) {
			if (!function.isVarArgs()) {
				Type required = function.getArgumentTypes().get(i);

				if (value.getType() == PrimitiveType.INTEGER && required == PrimitiveType.DECIMAL) {
					value = new DecimalValue(((IntegerValue) value).value().doubleValue());
				}

				// This check is necessary because of function references being untyped (issue #9).
				if (!required.accepts(value.getType())) {
					throw new InvalidFunctionParameterException(context, "Function \"" + function + "\" expected " + required + " as " + (i + 1) + (i == 0 ? "st" : i == 1 ? "nd" : i == 2 ? "rd" : "th") + " argument; received " + value.getType());
				}
			}

			list.add(value);
			i++;
		}

		return function.call(context, new FunctionCallDescriptor(this, getArguments(), list));

	}

	@Override
	public String toString() {
		return "functioncall(" + getFunctionName() + " <-- " + getArguments().toString() + ")";
	}
}