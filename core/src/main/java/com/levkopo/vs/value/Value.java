package com.levkopo.vs.value;

import com.levkopo.vs.type.Type;
import jdk.internal.org.objectweb.asm.MethodVisitor;

public abstract class Value implements Cloneable {
	private final Type type;

	public Value(Type type) {
		this.type = type;
	}

	public Value and(Value value) {
		return null;
	}

	@Override
	public abstract Value clone();

	public Integer compareTo(Value value) {
		return null;
	}

	public Value divide(Value value) {
		return null;
	}

	public BoolValue equals(Value value) {
		if(value()==null)
			return new BoolValue(value.value()==null);
		else if(value.value()==null)
			return new BoolValue(value()==null);

		return new BoolValue(value().equals(value.value()));
	}

	public final Type getType() {
		return type;
	}

	public MethodVisitor visit(MethodVisitor visitor){
		visitor.visitLdcInsn(value());
		return visitor;
	}

	public Value higherEqualThan(Value value) {
		Integer comparation = compareTo(value);

		return comparation != null ? new BoolValue(comparation >= 0) : null;
	}

	public Value higherThan(Value value) {
		Integer comparation = compareTo(value);

		return comparation != null ? new BoolValue(comparation > 0) : null;
	}

	public Value lowerEqualThan(Value value) {
		Integer comparation = compareTo(value);

		return comparation != null ? new BoolValue(comparation <= 0) : null;
	}

	public Value lowerThan(Value value) {
		Integer comparation = compareTo(value);

		return comparation != null ? new BoolValue(comparation < 0) : null;
	}

	public Value minus(Value value) {
		return null;
	}

	public Value multiply(Value value) {
		return null;
	}

	public Value negate() {
		return null;
	}

	public Value not() {
		return null;
	}

	public Value or(Value value) {
		return null;
	}

	public Value plus(Value value) {
		if (value instanceof StringValue) {
			StringValue string = (StringValue) value;

			return new StringValue(value() + string.value());
		}

		return null;
	}

	public Value remainder(Value value) {
		return null;
	}

	public Value shiftLeft(Value value) {
		return null;
	}

	public Value shiftRight(Value value) {
		return null;
	}

	public abstract Object value();

	// TODO Not OO, but fast (no need to use Reflection, etc)
	public static Value construct(Object object) {
		if (object instanceof Boolean) {
			return new BoolValue((Boolean) object);
		}

		if (object instanceof CharSequence) {
			return new StringValue(object.toString());
		}

		if (object instanceof Double || object instanceof Float) {
			return new DecimalValue((Double) object);
		}

		if (object instanceof Number) {
			return new IntegerValue(((Number) object).longValue());
		}

		if (object instanceof Type) {
			return new TypeValue((Type) object);
		}

		return null;
	}
}