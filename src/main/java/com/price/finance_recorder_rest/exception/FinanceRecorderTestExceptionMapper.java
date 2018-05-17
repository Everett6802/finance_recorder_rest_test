package com.price.finance_recorder_rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.price.finance_recorder_rest.common.CmnDef;
import com.price.finance_recorder_rest.common.ErrorMessage;


@Provider
public class FinanceRecorderTestExceptionMapper implements ExceptionMapper<FinanceRecorderTestException>
{
	@Override
	public Response toResponse(FinanceRecorderTestException exception)
	{
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ExceptionType.RESOURCE_NOT_FOUND.name(),
				CmnDef.URL_REF);
		
		return Response.status(Response.Status.CONFLICT).entity(errorMessage).build();
	}
}
