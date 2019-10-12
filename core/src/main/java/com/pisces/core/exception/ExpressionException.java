package com.pisces.core.exception;

public class ExpressionException extends BaseException {
	private static final long serialVersionUID = -8094703802819527529L;

	public ExpressionException(Enum<?> key, Object... args) {
		super(key, args);
	}
}
