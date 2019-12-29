package com.pisces.platform.integration.exception;

import com.pisces.platform.core.exception.BaseException;

public class ImportException extends BaseException {
	private static final long serialVersionUID = 2192139860067371697L;

	public ImportException(Enum<?> key, Object[] args) {
		super(key, args);
	}

}
