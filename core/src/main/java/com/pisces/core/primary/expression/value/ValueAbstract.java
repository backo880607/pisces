package com.pisces.core.primary.expression.value;

import com.pisces.core.config.CoreMessage;
import com.pisces.core.exception.ExpressionException;

public abstract class ValueAbstract {
	public abstract Type getType();
	
	public abstract Object getValue();
	
	public abstract Class<?> getReturnClass();
	
	private String formatType(ValueAbstract value) {
		return value.getReturnClass() != null ? value.getReturnClass().getName() : "null";
	}
	
	public ValueAbstract add(ValueAbstract rhs) {
		throw new ExpressionException(CoreMessage.NotSupportOperation, formatType(this) + " + " + formatType(rhs));
	}
	public ValueAbstract sub(ValueAbstract rhs) {
		throw new ExpressionException(CoreMessage.NotSupportOperation, formatType(this) + " - " + formatType(rhs));
	}
	public ValueAbstract multiply(ValueAbstract rhs) {
		throw new ExpressionException(CoreMessage.NotSupportOperation, formatType(this) + " * " + formatType(rhs));
	}
	public ValueAbstract division(ValueAbstract rhs) {
		throw new ExpressionException(CoreMessage.NotSupportOperation, formatType(this) + " / " + formatType(rhs));
	}
	
	public ValueBoolean greater(ValueAbstract rhs) {
		throw new ExpressionException(CoreMessage.NotSupportOperation, formatType(this) + " > " + formatType(rhs));
	}
	public ValueBoolean greaterEqual(ValueAbstract rhs) {
		throw new ExpressionException(CoreMessage.NotSupportOperation, formatType(this) + " >= " + formatType(rhs));
	}
	public ValueBoolean less(ValueAbstract rhs) {
		throw new ExpressionException(CoreMessage.NotSupportOperation, formatType(this) + " < " + formatType(rhs));
	}
	public ValueBoolean lessEqual(ValueAbstract rhs) {
		throw new ExpressionException(CoreMessage.NotSupportOperation, formatType(this) + " <= " + formatType(rhs));
	}
	public ValueBoolean equal(ValueAbstract rhs) {
		throw new ExpressionException(CoreMessage.NotSupportOperation, formatType(this) + " == " + formatType(rhs));
	}
	public ValueBoolean notEqual(ValueAbstract rhs) {
		throw new ExpressionException(CoreMessage.NotSupportOperation, formatType(this) + " != " + formatType(rhs));
	}
	
	public ValueText toText() {
		throw new ExpressionException(CoreMessage.NotSupportOperation, formatType(this) + " toText");
	}
	public ValueInt toInt() {
		throw new ExpressionException(CoreMessage.NotSupportOperation, formatType(this) + " toInt");
	}
	public ValueDouble toDouble() {
		throw new ExpressionException(CoreMessage.NotSupportOperation, formatType(this) + " toDouble");
	}
}
