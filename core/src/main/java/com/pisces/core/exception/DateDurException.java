package com.pisces.core.exception;

public class DateDurException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4164353564443586995L;

	public DateDurException() {
		super();
	}
	
	public DateDurException(String message) {
		super(message);
	}
	
	public DateDurException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DateDurException(Throwable cause) {
		super(cause);
	}
}
