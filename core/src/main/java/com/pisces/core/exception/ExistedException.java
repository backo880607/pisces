package com.pisces.core.exception;

public class ExistedException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8682468377397594960L;

	public ExistedException() {
		super();
	}
	
	public ExistedException(String message) {
		super(message);
	}
	
	public ExistedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ExistedException(Throwable cause) {
		super(cause);
	}
}
