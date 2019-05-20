package com.pisces.core.exception;

public class ExpressionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8094703802819527529L;

	public ExpressionException() {
		super();
	}
	
	public ExpressionException(String message) {
		super(message);
	}
	
	public ExpressionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ExpressionException(Throwable cause) {
		super(cause);
	}
}
