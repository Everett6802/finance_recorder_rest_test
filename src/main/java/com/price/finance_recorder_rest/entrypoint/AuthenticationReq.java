package com.price.finance_recorder_rest.entrypoint;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class AuthenticationReq {
	private String userName;
    private String userPassword;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
