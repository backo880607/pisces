package com.pisces.core.exception;

public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7541134573166923226L;

	public BaseException() {
		super();
	}
	
	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BaseException(Throwable cause) {
		super(cause);
	}
}
