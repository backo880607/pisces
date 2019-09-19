package com.pisces.core.exception;

public class ParameterException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7104264864839175793L;

	public ParameterException() {
		super();
	}
	
	public ParameterException(String message) {
		super(message);
	}
	
	public ParameterException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ParameterException(Throwable cause) {
		super(cause);
	}
}
