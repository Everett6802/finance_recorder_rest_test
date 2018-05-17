package com.price.finance_recorder_rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.price.finance_recorder_rest.common.CmnDef;
import com.price.finance_recorder_rest.common.ErrorMessage;


public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException>{
    public Response toResponse(AuthenticationException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                ExceptionType.AUTHENTICATION_FAILED.name(),
                CmnDef.URL_REF
        );
        
        return Response.status(Response.Status.UNAUTHORIZED).
                entity(errorMessage).
                build();
    }
}
