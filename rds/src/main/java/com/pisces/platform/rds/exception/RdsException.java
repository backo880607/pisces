package com.pisces.platform.rds.exception;

import com.pisces.platform.core.exception.BaseException;

public class RdsException extends BaseException {
    public RdsException(Enum<?> enumKey, Object... argements) {
        super(enumKey, argements);
    }
}
