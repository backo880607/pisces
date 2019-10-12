package com.pisces.user.exception;

public class UnRegisteredException extends UserException {
	private static final long serialVersionUID = -5838160633388320158L;

	public UnRegisteredException() {
		super();
	}
	
	public UnRegisteredException(String message) {
		super(message);
	}
	
	public UnRegisteredException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UnRegisteredException(Throwable cause) {
		super(cause);
	}
}
