package com.pisces.integration.exception;

import com.pisces.core.exception.BaseException;

public class ImportException extends BaseException {
	private static final long serialVersionUID = 2192139860067371697L;

	public ImportException(Enum<?> key, Object[] args) {
		super(key, args);
	}

}
