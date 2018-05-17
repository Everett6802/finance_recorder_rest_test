package com.price.finance_recorder_rest.exception;

public enum ExceptionType
{
	API_TEST_ERROR("API Test Error"), 
	RESOURCE_NOT_FOUND("Missing Resource File"), 
	MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    NO_RECORD_FOUND("Record with provided id is not found"),
    AUTHENTICATION_FAILED("Authentication failed"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified"),
    INTERNAL_SERVER_ERROR("Internal Server Error");
	
	private String exceptionMessage;
	
	ExceptionType(String exceptionMessage)
	{
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	
}
