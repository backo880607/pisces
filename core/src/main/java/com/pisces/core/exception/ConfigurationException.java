package com.pisces.core.exception;

public class ConfigurationException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3824627521737787544L;

	public ConfigurationException() {
		super();
	}
	
	public ConfigurationException(String message) {
		super(message);
	}
	
	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ConfigurationException(Throwable cause) {
		super(cause);
	}
}
