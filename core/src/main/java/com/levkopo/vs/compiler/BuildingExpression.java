package com.levkopo.vs.compiler;

import com.levkopo.vs.exception.compile.UnexpectedTokenException;
import com.levkopo.vs.expression.*;
import com.levkopo.vs.operator.BinaryOperator;
import com.levkopo.vs.operator.Operator;
import com.levkopo.vs.operator.OperatorList;
import com.levkopo.vs.operator.UnaryOperator;

import java.util.*;

public class BuildingExpression {
	private final List<String> inContext = new ArrayList<>();
	private final List<Object> values = new ArrayList<>();
	private Operator operator;
	private Expression expression;
	private final Stack<UnaryOperator> unaryWhenAlready;
	private final static Map<Operator, Integer> binopPrecedence = new HashMap<>();

	public BuildingExpression() {
		this.unaryWhenAlready = new Stack<>();
		binopPrecedence.put(OperatorList.PLUS, 1);
		binopPrecedence.put(OperatorList.MINUS, 1);
		binopPrecedence.put(OperatorList.MULTIPLY, 2);
		binopPrecedence.put(OperatorList.DIVIDE, 2);
		binopPrecedence.put(OperatorList.OR, 3);
		binopPrecedence.put(OperatorList.AND, 3);
	}

	public void addInContext(VenusParser parser, Token owner, String context) throws UnexpectedTokenException {
		this.inContext.add(context);
	}

	public void addOperator(VenusParser parser, Token owner, Operator op) throws UnexpectedTokenException {
		values.add(op);
		if (hasOperator()) {
			if (op instanceof UnaryOperator) {
				unaryWhenAlready.push((UnaryOperator) op);

				return;
			} else {
				parser.bye(owner, "already have an operator \"" + operator + "\"");
			}
		}

		if (op instanceof BinaryOperator && !hasResultor()) {
			parser.bye(owner, "no left operation value");
		}

		if (op instanceof UnaryOperator && hasResultor()) {
			parser.bye(owner, "cannot apply unary operator to left-sided value");
		}

		this.operator = op;
	}

	public void addExpression(VenusParser parser, Token owner, Expression rslt) throws UnexpectedTokenException {
		values.add(rslt);
		if (!hasResultor()) {
			if (operator instanceof UnaryOperator) {
				while (!unaryWhenAlready.isEmpty()) {
					rslt = new UnaryOperation(unaryWhenAlready.pop(), rslt);
				}

				setExpression(new UnaryOperation((UnaryOperator) operator, rslt));
			} else {
				setExpression(rslt);
			}
		} else if (hasOperator()) {
			if (operator instanceof BinaryOperator) {
				while (!unaryWhenAlready.isEmpty()) {
					rslt = new UnaryOperation(unaryWhenAlready.pop(), rslt);
				}

				setExpression(new BinaryOperation((BinaryOperator) operator, expression, rslt));
			} else {
				parser.bye("Excepted a binary or unary operator, received " + operator.getClass().getSimpleName());
			}

			this.operator = null;
		} else {
			parser.bye(owner, "expected operator, found another expression");
		}
	}

	public Expression build() {
		return expression;
	}

	public Expression parseBinOpRHS(int exprPrec, Expression lhs, int num) {
		while (true){
			int tokPrec = getTokPrecedence((Operator) values.get(num));
			if(tokPrec<exprPrec)
				return lhs;

			Operator op = (Operator) values.get(num);
			Expression rhs = (Expression) values.get(num+1);

			if(values.size()<(num+2)) {
				int nextPrec = getTokPrecedence((Operator) values.get(num++));
				if (tokPrec < nextPrec) {
					rhs = parseBinOpRHS(tokPrec + 1, lhs, num);
					if (rhs == null) return null;
				}
			}

			lhs = new BinaryOperation((BinaryOperator) op, lhs, rhs);
		}
	}

	public boolean hasOperator() {
		return  operator!=null;
		//return values.get(values.size()-1) instanceof Operator;
	}

	public boolean hasResultor() {
		return expression!=null;
		//return values.get(values.size()-1) instanceof Expression;
	}

	private void setExpression(Expression expression) {
		if (inContext.size()!=0) {
			InContext expressionTmp = new InContext(inContext.get(0), inContext.size()==1?
					expression:new Variable(inContext.get(1)));

			if(inContext.size()>1) {
				for (int i = inContext.size() - 1; i != 1; i--) {
					expressionTmp = new InContext(expressionTmp, new Variable(inContext.get(i)));
				}

				expression = new InContext(expressionTmp, expression);
			}else expression = expressionTmp;


			this.inContext.clear();
		}

		this.expression = expression;
	}

	protected int getTokPrecedence(Operator op) {
		if(!binopPrecedence.containsKey(op))
			return -1;

		int tokPrecedence = binopPrecedence.get(op);
		if(tokPrecedence<=0) return -1;

		return tokPrecedence;
	}
}
