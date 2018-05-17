package com.price.finance_recorder_rest.exception;

public class EmailVerificationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1913958411543769643L;

    public EmailVerificationException(String message)
    {
        super(message);
    }
}
