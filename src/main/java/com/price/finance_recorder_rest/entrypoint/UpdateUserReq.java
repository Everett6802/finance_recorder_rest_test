package com.price.finance_recorder_rest.entrypoint;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class UpdateUserReq {
    private String firstName;
    private String lastName;

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
