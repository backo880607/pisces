package com.pisces.core.primary.expression.exception;

import com.pisces.core.exception.ExpressionException;

public class ValueException extends ExpressionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7406054203138053102L;

	public ValueException() {
		super();
	}
	
	public ValueException(String message) {
		super(message);
	}
	
	public ValueException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ValueException(Throwable cause) {
		super(cause);
	}
}
