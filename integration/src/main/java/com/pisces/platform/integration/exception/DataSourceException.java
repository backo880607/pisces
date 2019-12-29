package com.pisces.platform.integration.exception;

import com.pisces.platform.core.exception.BaseException;

public class DataSourceException extends BaseException {
    private static final long serialVersionUID = 4370602859519589846L;

    public DataSourceException(Enum<?> key, Object... args) {
        super(key, args);
    }

}
