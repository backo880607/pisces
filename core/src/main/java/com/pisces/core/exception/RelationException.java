package com.pisces.core.exception;

public class RelationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5774707103060725987L;

	public RelationException() {
		super();
	}
	
	public RelationException(String message) {
		super(message);
	}
	
	public RelationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RelationException(Throwable cause) {
		super(cause);
	}
}
