package com.price.finance_recorder_rest.exception;

public class CouldNotDeleteRecordException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2963377442474298145L;

	public CouldNotDeleteRecordException(String message)
    {
        super(message);
    }
}
