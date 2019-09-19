package com.pisces.core.exception;

public class RegisteredException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2629496305888046864L;

	public RegisteredException() {
		super();
	}
	
	public RegisteredException(String message) {
		super(message);
	}
	
	public RegisteredException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RegisteredException(Throwable cause) {
		super(cause);
	}
}
