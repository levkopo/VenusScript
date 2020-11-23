package com.levkopo.vs.compiler;

import com.levkopo.vs.compiler.Token.Type;
import com.levkopo.vs.exception.compile.UnexpectedInputException;
import com.levkopo.vs.origin.ScriptOrigin;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import static com.levkopo.vs.compiler.VenusLexer.State.*;

public class VenusLexer {
	private final StringBuilder buildingToken;
	private boolean insideComment;
	private char lastChar;
	private int line;
	private Type numberLiteralType;
	private final ScriptOrigin origin;
	private int position;
	private final Queue<Token> reread;
	private State state;
	private final String string;

	public VenusLexer(ScriptOrigin origin) throws IOException {
		this.buildingToken = new StringBuilder();
		this.origin = origin;
		this.reread = new ArrayDeque<>();
		this.string = origin.read();
	}

	public int currentLine() {
		return line;
	}

	public Token nextToken() throws UnexpectedInputException {
		if (!reread.isEmpty()) {
			return reread.poll();
		}

		while (canRead()) {
			char ch = read();

			try {
				boolean isDigit = Character.isDigit(ch);
				boolean isLetter = Character.isLetter(ch) || ch == '_';

				if (ch == '\n') {
					this.insideComment = false;

					if (state == IN_CHAR_LITERAL) {
						bye("End of line found, but unclosed character literal");
					}

					if (state == IN_STRING_LITERAL) {
						bye("End of line found, but unclosed string literal");
					}

					if (state == IN_NAME_DEFINITION) {
						back();

						this.state = null;

						Token token = new Token(Type.NAME_DEFINITION, buildingToken.toString());
						buildingToken.setLength(0);
						return token;
					}

					if (state == IN_NUMBER_LITERAL) {
						back();

						this.state = null;

						Token token = new Token(numberLiteralType, buildingToken.toString());
						buildingToken.setLength(0);
						return token;
					}

					this.line++;

					return new Token(Type.NEW_LINE, null);
				}

				if (insideComment) {
					continue;
				}

				if (ch == '"' && lastChar != '\\' && state != IN_CHAR_LITERAL) {
					if (state == IN_STRING_LITERAL) {
						this.state = null;

						Token token = new Token(Type.STRING_LITERAL, buildingToken.toString());
						buildingToken.setLength(0);
						return token;
					}

					if (state == IN_NUMBER_LITERAL) {
						bye("Double quotes found while parsing a number literal");
					}

					if (state == IN_NAME_DEFINITION) {
						bye("Double quotes found while parsing a name definition");
					}

					this.state = IN_STRING_LITERAL;

					continue;
				}

				if (ch == '\'' && lastChar != '\\' && state != IN_STRING_LITERAL) {
					if (state == IN_CHAR_LITERAL) {
						this.state = null;

						Token token = new Token(Type.CHAR_LITERAL, buildingToken.toString());
						buildingToken.setLength(0);
						return token;
					}

					if (state == IN_NUMBER_LITERAL) {
						bye("Single quote found while parsing a number literal");
					}

					if (state == IN_NAME_DEFINITION) {
						bye("Single quote found while parsing a name definition");
					}

					this.state = IN_CHAR_LITERAL;

					continue;
				}

				if (state == IN_NUMBER_LITERAL) {
					if (ch == 'b' && buildingToken.length() == 1 && buildingToken.charAt(0) == '0') {
						if (numberLiteralType == Type.DECIMAL_LITERAL) {
							this.numberLiteralType = Type.BINARY_LITERAL;

							buildingToken.setLength(0);

							continue;
						} else {
							bye("Cannot re-define number literal type (already " + numberLiteralType + ')');
						}
					}

					if (ch == 'x' && buildingToken.length() == 1 && buildingToken.charAt(0) == '0') {
						if (numberLiteralType == Type.DECIMAL_LITERAL) {
							this.numberLiteralType = Type.HEXADECIMAL_LITERAL;

							buildingToken.setLength(0);

							continue;
						} else {
							bye("Cannot re-define number literal type (already " + numberLiteralType + ')');
						}
					}

					boolean condition = false;

					if (numberLiteralType == Type.BINARY_LITERAL) {
						if (isLetter) {
							if (ch=='0'||ch=='1') {
								bye("Invalid binary character \"" + ch + "\"");
							}
						} else {
							condition = !isDigit;
						}
					} else if (numberLiteralType == Type.DECIMAL_LITERAL) {
						if (isLetter) {
							bye("Letter found while parsing a number literal");
						}

						condition = !isDigit && ch != '.';
					} else if (numberLiteralType == Type.HEXADECIMAL_LITERAL) {
						if (isLetter) {
							if ((ch+"").matches("-?[0-9a-fA-F]+")) {
								bye("Invalid hexadecimal character \"" + ch + "\"");
							}
						} else {
							condition = !isDigit;
						}
					}

					if (condition) {
						back();

						this.state = null;

						Token token = new Token(numberLiteralType, buildingToken.toString());
						buildingToken.setLength(0);
						return token;
					}
				} else if (state != IN_CHAR_LITERAL && state != IN_STRING_LITERAL) {
					if (ch == ' ' || ch == '\t') {
						if (state == IN_NAME_DEFINITION) {
							this.state = null;

							Token token = new Token(Type.NAME_DEFINITION, buildingToken.toString());
							buildingToken.setLength(0);
							return token;
						}

						continue;
					}

					if (state == IN_NAME_DEFINITION && !isLetter && !isDigit) {
						back();

						this.state = null;

						Token token = new Token(Type.NAME_DEFINITION, buildingToken.toString());
						buildingToken.setLength(0);
						return token;
					}

					if (!isLetter && !isDigit) {
						if (ch == ',') {
							return new Token(Type.COMMA, ch);
						}

						if (ch == KeywordDefinitions.FUNCTION_REFERENCE) {
							return new Token(Type.FUNC_REF, ch);
						}

						if(ch == '\uFEFF')
							return new Token(Type.NEW_LINE, ch);

						if (ch == KeywordDefinitions.COLON) {
							return new Token(Type.COLON, ch);
						}

						if (ch == KeywordDefinitions.GLOBAL_ACCESS) {
							return new Token(Type.GLOBAL_ACCESS, ch);
						}

						if (ch == KeywordDefinitions.OBJECT_ACCESS) {
							return new Token(Type.OBJECT_ACCESS, ch);
						}

						if (ch == '{') {
							return new Token(Type.OPEN_BRACE, ch);
						}

						if (ch == '[') {
							return new Token(Type.OPEN_BRACKET, ch);
						}

						if (ch == '(') {
							return new Token(Type.OPEN_PARENTHESE, ch);
						}

						if (ch == '}') {
							return new Token(Type.CLOSE_BRACE, ch);
						}

						if (ch == ']') {
							return new Token(Type.CLOSE_BRACKET, ch);
						}

						if (ch == ')') {
							return new Token(Type.CLOSE_PARENTHESE, ch);
						}

						if (ch == KeywordDefinitions.COMMENTER) {
							this.insideComment = true;

							continue;
						}

						return new Token(Type.OPERATOR, ch);
					}

					if (isDigit && state != IN_NAME_DEFINITION) {
						this.numberLiteralType = Type.DECIMAL_LITERAL;
						this.state = IN_NUMBER_LITERAL;
					} else if (isLetter && state != IN_NAME_DEFINITION) {
						this.state = IN_NAME_DEFINITION;
					}
				}

				if (lastChar == '\\') {
					buildingToken.append(unescapeJavaString("\\" + ch));
					ch = 0;
				} else if (ch != '\\') {
					buildingToken.append(ch);
				}
			} finally {
				this.lastChar = ch;
			}
		}

		return null;
	}

	public String unescapeJavaString(String st) {

		StringBuilder sb = new StringBuilder(st.length());

		for (int i = 0; i < st.length(); i++) {
			char ch = st.charAt(i);
			if (ch == '\\') {
				char nextChar = (i == st.length() - 1) ? '\\' : st
						.charAt(i + 1);
				// Octal escape?
				if (nextChar >= '0' && nextChar <= '7') {
					String code = "" + nextChar;
					i++;
					if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
							&& st.charAt(i + 1) <= '7') {
						code += st.charAt(i + 1);
						i++;
						if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
								&& st.charAt(i + 1) <= '7') {
							code += st.charAt(i + 1);
							i++;
						}
					}
					sb.append((char) Integer.parseInt(code, 8));
					continue;
				}
				switch (nextChar) {
					case '\\':
						ch = '\\';
						break;
					case 'b':
						ch = '\b';
						break;
					case 'f':
						ch = '\f';
						break;
					case 'n':
						ch = '\n';
						break;
					case 'r':
						ch = '\r';
						break;
					case 't':
						ch = '\t';
						break;
					case '\"':
						ch = '\"';
						break;
					case '\'':
						ch = '\'';
						break;
					// Hex Unicode: u????
					case 'u':
						if (i >= st.length() - 5) {
							ch = 'u';
							break;
						}
						int code = Integer.parseInt(
								"" + st.charAt(i + 2) + st.charAt(i + 3)
										+ st.charAt(i + 4) + st.charAt(i + 5), 16);
						sb.append(Character.toChars(code));
						i += 5;
						continue;
				}
				i++;
			}
			sb.append(ch);
		}
		return sb.toString();
	}

	public void reRead(Token token) {
		reread.add(token);
	}

	protected void back() {
		this.position--;
	}

	protected void bye(String message) throws UnexpectedInputException {
		throw new UnexpectedInputException(origin.getScriptName(), currentLine(), message);
	}

	protected boolean canRead() {
		return position <= string.length();
	}

	protected char read() {
		if (position == string.length()) {
			this.line++;
			this.position++;

			return '\n';
		}

		return string.charAt(position++);
	}

	public enum State {
		IN_STRING_LITERAL,
		IN_NUMBER_LITERAL,
		IN_CHAR_LITERAL,
		IN_NAME_DEFINITION
	}
}
