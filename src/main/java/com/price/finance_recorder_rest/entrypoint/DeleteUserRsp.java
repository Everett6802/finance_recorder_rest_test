package com.price.finance_recorder_rest.entrypoint;

import javax.xml.bind.annotation.XmlRootElement;

import com.price.finance_recorder_rest.common.CmnDef.RequestOperation;
import com.price.finance_recorder_rest.common.CmnDef.ResponseStatus;

@XmlRootElement
public class DeleteUserRsp {
	private RequestOperation requestOperation;
    private ResponseStatus responseStatus;

    public RequestOperation getRequestOperation() {
		return requestOperation;
	}
	public void setRequestOperation(RequestOperation requestOperation) {
		this.requestOperation = requestOperation;
	}
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}
}
