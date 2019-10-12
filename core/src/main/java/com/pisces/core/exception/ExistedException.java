package com.pisces.core.exception;

public class ExistedException extends BaseException {
	private static final long serialVersionUID = -8682468377397594960L;

	public ExistedException(Enum<?> key, Object... args) {
		super(key, args);
	}
}
