package com.pisces.core.exception;

public class DeleteException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8800654036840858427L;

	public DeleteException() {
		super();
	}
	
	public DeleteException(String message) {
		super(message);
	}
	
	public DeleteException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DeleteException(Throwable cause) {
		super(cause);
	}
}
