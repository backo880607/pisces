package com.pisces.core.exception;

public class LicenseException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2982990613157147413L;

	public LicenseException(Enum<?> key, Object... args) {
		super(key, args);
	}
}
