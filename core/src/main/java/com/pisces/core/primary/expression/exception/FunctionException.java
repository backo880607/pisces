package com.pisces.core.primary.expression.exception;

import com.pisces.core.exception.ExpressionException;

public class FunctionException extends ExpressionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8601780505773764823L;

	public FunctionException() {
		super();
	}
	
	public FunctionException(String message) {
		super(message);
	}
	
	public FunctionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public FunctionException(Throwable cause) {
		super(cause);
	}
}
