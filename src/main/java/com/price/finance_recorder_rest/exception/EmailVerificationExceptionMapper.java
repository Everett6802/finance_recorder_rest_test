package com.price.finance_recorder_rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.price.finance_recorder_rest.common.CmnDef;
import com.price.finance_recorder_rest.common.ErrorMessage;

@Provider
public class EmailVerificationExceptionMapper implements ExceptionMapper<EmailVerificationException>{

	@Override
    public Response toResponse(EmailVerificationException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                ExceptionType.EMAIL_ADDRESS_NOT_VERIFIED.name(),
                CmnDef.URL_REF
        );
        
        return Response.status(Response.Status.FORBIDDEN).
                entity(errorMessage).
                build();
    }

}
