package com.security.app.exception;

public class AppSecurityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AppSecurityException() {
		super();
	}

	public AppSecurityException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AppSecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppSecurityException(String message) {
		super(message);
	}

	public AppSecurityException(Throwable cause) {
		super(cause);
	}

}
