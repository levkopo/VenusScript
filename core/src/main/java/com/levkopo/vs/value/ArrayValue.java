package com.levkopo.vs.value;

import com.levkopo.vs.exception.runtime.InvalidArrayAccessException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.type.PrimitiveType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ArrayValue extends IterableValue {
	private final List<Value> values = new ArrayList<>();

	public ArrayValue(Value... values) {
		super(PrimitiveType.ARRAY);

		this.values.addAll(Arrays.asList(values));
	}

	@Override
	public ArrayValue clone() {
		return new ArrayValue(values.toArray(new Value[0]));
	}

	@Override
	public BoolValue equals(Value value) {
		if (value instanceof ArrayValue) {
			ArrayValue array = (ArrayValue) value;

			return new BoolValue(size() == array.size() && value().equals(array.value()));
		}

		return new BoolValue(false);
	}

	public Value get(Context context, int index) throws ScriptRuntimeException {
		if (index < 0 || index >= size()) {
			throw new InvalidArrayAccessException(context, "Out of range array index: " + index + ", expected between 0~" + (size() - 1));
		}

		return value().get(index);
	}

	@Override
	public Iterator<Value> iterator() {
		return values.iterator();
	}

	public void set(int index, Value value) {
		value().add(index, value);
	}

	public int size() {
		return value().size();
	}

	@Override
	public String toString() {
		return toString(this);
	}

	@Override
	public List<Value> value() {
		return values;
	}

	private static String toString(ArrayValue array) {
		return array.value().toString();
	}
}
