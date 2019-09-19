package com.pisces.core.exception;

public class OperandException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4581414514429464217L;

	public OperandException() {
		super();
	}
	
	public OperandException(String message) {
		super(message);
	}
	
	public OperandException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public OperandException(Throwable cause) {
		super(cause);
	}
}
