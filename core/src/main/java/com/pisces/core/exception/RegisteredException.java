package com.pisces.core.exception;

public class RegisteredException extends BaseException {
	private static final long serialVersionUID = -2629496305888046864L;

	public RegisteredException(Enum<?> key, Object... args) {
		super(key, args);
	}
}
