package com.pisces.integration.exception;

import com.pisces.core.exception.BaseException;

public class ExportException extends BaseException {
	private static final long serialVersionUID = 4221368763724833565L;

	public ExportException(Enum<?> key, Object[] args) {
		super(key, args);
	}

}
