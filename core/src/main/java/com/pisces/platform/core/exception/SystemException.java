package com.pisces.platform.core.exception;

public class SystemException extends RuntimeException {
    private static final long serialVersionUID = 4078215306662957769L;

    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }
}
