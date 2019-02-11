package com.test.demo.security.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public ValidateCodeException(String explanation) {
		super(explanation);
	}

}
