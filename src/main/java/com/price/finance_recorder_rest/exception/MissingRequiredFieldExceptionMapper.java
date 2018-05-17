package com.price.finance_recorder_rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.price.finance_recorder_rest.common.CmnDef;
import com.price.finance_recorder_rest.common.ErrorMessage;


public class MissingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException> {
    
	public Response toResponse(MissingRequiredFieldException exception) {
// Create the error message for response
    	ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
                ExceptionType.MISSING_REQUIRED_FIELD.name(), CmnDef.URL_REF);
// Sent to response
      return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
