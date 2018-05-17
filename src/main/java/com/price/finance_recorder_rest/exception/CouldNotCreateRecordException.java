package com.price.finance_recorder_rest.exception;

public class CouldNotCreateRecordException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4752609889011676815L;

    public CouldNotCreateRecordException(String message)
    {
        super(message);
    }
}
