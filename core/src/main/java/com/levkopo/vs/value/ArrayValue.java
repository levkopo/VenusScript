package com.levkopo.vs.value;

import com.levkopo.vs.exception.runtime.InvalidArrayAccessException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.type.PrimitiveType;
import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayValue extends IterableValue {
	private final Value[] values;

	public ArrayValue(int size) {
		this(new Value[size]);
	}

	public ArrayValue(Value... values) {
		super(PrimitiveType.ARRAY);

		this.values = values;
	}

	@Override
	public ArrayValue clone() {
		return new ArrayValue(Arrays.copyOf(values, values.length));
	}

	@Override
	public BoolValue equals(Value value) {
		if (value instanceof ArrayValue) {
			ArrayValue array = (ArrayValue) value;

			return new BoolValue(size() == array.size() && Arrays.equals(value(), array.value()));
		}

		return new BoolValue(false);
	}

	public Value get(Context context, int index) throws ScriptRuntimeException {
		if (index < 0 || index >= size()) {
			throw new InvalidArrayAccessException(context, "Out of range array index: " + index + ", expected between 0~" + (size() - 1));
		}

		return value()[index];
	}

	@Override
	public Iterator<Value> iterator() {
		return Arrays.stream(values).iterator();
	}

	public void set(Context context, int index, Value value) throws ScriptRuntimeException {
		if (index < 0 || index >= size()) {
			throw new InvalidArrayAccessException(context, "Out of range array index: " + index + ", expected between 0~" + (size() - 1));
		}

		value()[index] = value;
	}

	public int size() {
		return value().length;
	}

	@Override
	public String toString() {
		return toString(this);
	}

	@Override
	public Value[] value() {
		return values;
	}

	private static String toString(ArrayValue array) {
		return Arrays.toString(array.value());
	}
}
