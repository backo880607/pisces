package com.pisces.core.primary.expression.value;

import com.pisces.core.exception.OperandException;

public abstract class ValueAbstract {
	public abstract Type getType();
	
	public abstract Object getValue();
	
	public ValueAbstract add(ValueAbstract rhs) {
		throw new OperandException(getValue() + " + " + rhs.getValue() + " is not supported!");
	}
	public ValueAbstract sub(ValueAbstract rhs) {
		throw new OperandException(getValue() + " - " + rhs.getValue() + " is not supported!");
	}
	public ValueAbstract multiply(ValueAbstract rhs) {
		throw new OperandException(getValue() + " * " + rhs.getValue() + " is not supported!");
	}
	public ValueAbstract division(ValueAbstract rhs) {
		throw new OperandException(getValue() + " / " + rhs.getValue() + " is not supported!");
	}
	
	public ValueBoolean greater(ValueAbstract rhs) {
		throw new OperandException(getValue() + " > " + rhs.getValue() + " is not supported!");
	}
	public ValueBoolean greaterEqual(ValueAbstract rhs) {
		throw new OperandException(getValue() + " >= " + rhs.getValue() + " is not supported!");
	}
	public ValueBoolean less(ValueAbstract rhs) {
		throw new OperandException(getValue() + " < " + rhs.getValue() + " is not supported!");
	}
	public ValueBoolean lessEqual(ValueAbstract rhs) {
		throw new OperandException(getValue() + " <= " + rhs.getValue() + " is not supported!");
	}
	public ValueBoolean equal(ValueAbstract rhs) {
		throw new OperandException(getValue() + " == " + rhs.getValue() + " is not supported!");
	}
	public ValueBoolean notEqual(ValueAbstract rhs) {
		throw new OperandException(getValue() + " != " + rhs.getValue() + " is not supported!");
	}
	
	public ValueText toText() {
		throw new OperandException(getValue() + " toText " + " is not supported!");
	}
	public ValueInt toInt() {
		throw new OperandException(getValue() + " toInt " + " is not supported!");
	}
	public ValueDouble toDouble() {
		throw new OperandException(getValue() + " toDouble " + " is not supported!");
	}
}
