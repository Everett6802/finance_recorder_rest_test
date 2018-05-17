package com.price.finance_recorder_rest.exception;

public class MissingRequiredFieldException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3122203559185148854L;

	public MissingRequiredFieldException(String message)
    {
        super(message);
    }
}
