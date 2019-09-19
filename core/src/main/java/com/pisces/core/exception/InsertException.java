package com.pisces.core.exception;

public class InsertException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1788142867932550033L;

	public InsertException() {
		super();
	}
	
	public InsertException(String message) {
		super(message);
	}
	
	public InsertException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InsertException(Throwable cause) {
		super(cause);
	}
}
