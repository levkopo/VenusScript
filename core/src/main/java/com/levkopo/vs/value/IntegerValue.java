package com.levkopo.vs.value;

import com.levkopo.vs.type.PrimitiveType;

public class IntegerValue extends NumericValue {
	private final long value;

	public IntegerValue(long value) {
		super(PrimitiveType.INTEGER);
		this.value = value;
	}

	@Override
	public Value and(Value value) {
		if (value instanceof IntegerValue) {
			return new IntegerValue(this.value & ((IntegerValue) value).value().longValue());
		}

		return super.and(value);
	}

	@Override
	public IntegerValue clone() {
		return new IntegerValue(value);
	}

	@Override
	public Integer compareTo(Value value) {
		if (value instanceof NumericValue) {
			NumericValue numeric = (NumericValue) value;

			return Long.compare(this.value, numeric.value().longValue());
		}

		return super.compareTo(value);
	}

	@Override
	public Value divide(Value value) {
		if (value instanceof NumericValue) {
			NumericValue numeric = (NumericValue) value;

			return new IntegerValue(this.value / numeric.value().longValue());
		}

		return super.divide(value);
	}

	@Override
	public Value minus(Value value) {
		if (value instanceof NumericValue) {
			NumericValue numeric = (NumericValue) value;

			return new IntegerValue(this.value - numeric.value().longValue());
		}

		return super.minus(value);
	}

	@Override
	public Value multiply(Value value) {
		if (value instanceof NumericValue) {
			NumericValue numeric = (NumericValue) value;

			return new IntegerValue(this.value * numeric.value().longValue());
		}

		return super.multiply(value);
	}

	@Override
	public Value negate() {
		return new IntegerValue(-value);
	}

	@Override
	public Value or(Value value) {
		if (value instanceof IntegerValue) {
			return new IntegerValue(this.value | (long) ((IntegerValue) value).value());
		}

		return super.or(value);
	}

	@Override
	public Value plus(Value value) {
		if (value instanceof NumericValue) {
			NumericValue numeric = (NumericValue) value;

			return new IntegerValue(this.value + numeric.value().longValue());
		}

		return super.plus(value);
	}

	@Override
	public Value remainder(Value value) {
		if (value instanceof NumericValue) {
			NumericValue numeric = (NumericValue) value;

			return new IntegerValue(this.value % numeric.value().longValue());
		}

		return super.remainder(value);
	}

	@Override
	public Value shiftLeft(Value value) {
		if (value instanceof IntegerValue) {
			IntegerValue integer = (IntegerValue) value;

			return new IntegerValue(this.value << (long) integer.value());
		}

		return super.shiftLeft(value);
	}

	@Override
	public Value shiftRight(Value value) {
		if (value instanceof IntegerValue) {
			IntegerValue integer = (IntegerValue) value;

			return new IntegerValue(this.value >> (long) integer.value());
		}

		return super.shiftRight(value);
	}

	@Override
	public String toString() {
		return Long.toString(value);
	}

	@Override
	public Number value() {
		if(value<=Integer.MAX_VALUE)
			return (int) value;
		else return value;
	}
}