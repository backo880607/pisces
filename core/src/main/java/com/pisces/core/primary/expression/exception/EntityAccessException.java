package com.pisces.core.primary.expression.exception;

import com.pisces.core.exception.ExpressionException;

public class EntityAccessException extends ExpressionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -611823018839458325L;

	public EntityAccessException() {
		super();
	}
	
	public EntityAccessException(String message) {
		super(message);
	}
	
	public EntityAccessException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public EntityAccessException(Throwable cause) {
		super(cause);
	}
}
