package com.levkopo.vs.type;

import com.levkopo.vs.expression.Variable;
import com.levkopo.vs.function.Function;
import com.levkopo.vs.operator.OperatorList;
import com.levkopo.vs.value.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class PrimitiveType extends Type {
	public static final Type ANY = new PrimitiveType("any", Value.class, Object.class);
	public static final Type ARRAY = new PrimitiveType("array", ArrayValue.class, Value[].class);
	public static final Type BOOLEAN = new PrimitiveType("bool", BoolValue.class, Boolean.class);
	public static final Type DECIMAL = new PrimitiveType("decimal", DecimalValue.class, Double.class, Float.class);
	public static final Type FUNCTION_REFERENCE = new PrimitiveType("ref", FunctionRefValue.class, Function.class);
	public static final Type INTEGER = new PrimitiveType("int", IntegerValue.class, Integer.class, Long.class);
	public static final Type STRING = new PrimitiveType("string", StringValue.class, String.class);
	public static final Type VOID = new PrimitiveType("void", Value.class, void.class);
	public static final Type TYPE = new PrimitiveType("type", TypeValue.class, Type.class);
	public static final Type MAP = new PrimitiveType("map", MapValue.class, Map.class);
	public static final Type VARIABLE_REFERENCE = new PrimitiveType("var", VariableRefValue.class, Variable.class);

	private final List<Class<?>> objectTypes;
	private final Class<? extends Value> valueClass;

	private PrimitiveType(String identifier, Class<? extends Value> valueClass, Class<?>... objectTypes) {
		super(identifier);
		this.objectTypes = Arrays.asList(objectTypes);
		this.valueClass = valueClass;
	}

	@Override
	public boolean accepts(Class<? extends Value> valueCl) {
		return valueClass.isAssignableFrom(valueCl);
	}

	@Override
	public boolean accepts(Type type) {
		return type instanceof PrimitiveType && accepts(((PrimitiveType) type).valueClass);
	}

	@Override
	public boolean objectAccepts(Class<?> type) {
		for(Class<?> object: objectTypes){
			if(!object.isAssignableFrom(type))
				return false;
		}

		return true;
	}

	public static Type forIdentifier(String identifier) {
		for (Type value : values()) {
			if (value.getIdentifier().equals(identifier)) {
				return value;
			}
		}

		return null;
	}

	public static Type forObjectType(Class<?> type) {
		for (Type value : values()) {
			if (value != ANY && value.objectAccepts(type)) {
				return value;
			}
		}

		return ANY;
	}

	public static Type forType(Class<? extends Value> type) {
		for (Type value : values()) {
			if (value != ANY && value.accepts(type)) {
				return value;
			}
		}

		return ANY;
	}

	public static List<PrimitiveType> values() {
		List<PrimitiveType> output = new ArrayList<>();

		for (Field f : PrimitiveType.class.getDeclaredFields())
			if (Modifier.isStatic(f.getModifiers()))
				try {
					output.add((PrimitiveType) f.get(PrimitiveType.class));
				} catch (IllegalAccessException ignored) {}

		return output;
	}
}
