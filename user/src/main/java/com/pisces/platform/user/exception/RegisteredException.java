package com.pisces.platform.user.exception;

public class RegisteredException extends UserException {
	private static final long serialVersionUID = -663947266774099088L;

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
