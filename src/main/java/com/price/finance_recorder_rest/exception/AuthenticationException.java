package com.price.finance_recorder_rest.exception;

public class AuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1663882195285128391L;

    public AuthenticationException(String message) {
        super(message);
    }
}
