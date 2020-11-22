package com.levkopo.vs.component.branch;

import com.levkopo.vs.component.Container;
import com.levkopo.vs.expression.Expression;

public class ForRangeContainer extends Container implements Breakable {
	private final Expression adjustment;
	private final Expression from;
	private final Expression to;
	private final String varName;

	public ForRangeContainer(String varName, Expression from, Expression to, Expression adjustment) {
		this.adjustment = adjustment;
		this.from = from;
		this.to = to;
		this.varName = varName;
	}

	public Expression getAdjustment() {
		return adjustment;
	}

	public Expression getFrom() {
		return from;
	}

	public Expression getTo() {
		return to;
	}

	public String getVarName() {
		return varName;
	}

	@Override
	public String toString() {
		return "for(" + getVarName() + " in [" + getFrom() + ", " + getTo() + "] with " + getAdjustment() + ')';
	}
}