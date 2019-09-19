package com.pisces.core.exception;

public class NotImplementedException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7119120162647215942L;

	public NotImplementedException() {
		super();
	}
	
	public NotImplementedException(String message) {
		super(message);
	}
	
	public NotImplementedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NotImplementedException(Throwable cause) {
		super(cause);
	}
}
