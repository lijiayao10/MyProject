package com.cjy.code.cxf.domain;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 7607640803750403555L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
