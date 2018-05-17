package com.price.finance_recorder_rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.price.finance_recorder_rest.common.CmnDef;
import com.price.finance_recorder_rest.common.ErrorMessage;


@Provider
public class FinanceRecorderResourceNotFoundExceptionMapper
		implements ExceptionMapper<FinanceRecorderResourceNotFoundException>
{
	
	@Override
	public Response toResponse(FinanceRecorderResourceNotFoundException exception)
	{
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ExceptionType.API_TEST_ERROR.name(),
				CmnDef.URL_REF);
		
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorMessage).build();
	}
}
