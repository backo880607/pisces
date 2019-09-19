package com.pisces.core.exception;

public class LicenseException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2982990613157147413L;

	public LicenseException() {
		super();
	}
	
	public LicenseException(String message) {
		super(message);
	}
	
	public LicenseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public LicenseException(Throwable cause) {
		super(cause);
	}
}
