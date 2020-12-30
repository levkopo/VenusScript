package com.levkopo.vs.compiler;

public class Token {
	private final Type type;
	private final String value;

	public Token(Type type, char value) {
		this(type, Character.toString(value));
	}

	public Token(Type type, String value) {
		this.type = type;
		this.value = value;
	}

	public Type getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		if (getValue() != null) {
			return getType().toString() + '[' + getValue() + ']';
		}

		return getType().toString();
	}

	public enum Type {
		NAME_DEFINITION,
		OPEN_BRACE,
		CLOSE_BRACE,
		OPEN_BRACKET,
		CLOSE_BRACKET,
		OPEN_PARENTHESE,
		CLOSE_PARENTHESE,
		BINARY_LITERAL,
		DECIMAL_LITERAL,
		HEXADECIMAL_LITERAL,
		STRING_LITERAL,
		CHAR_LITERAL,
		OPERATOR,
		COMMA,
		AT,
		NEW_LINE,
		COLON,
		GLOBAL_ACCESS,
		OBJECT_ACCESS,
		MAP_VALUE_DELIMETER,
	}
}
