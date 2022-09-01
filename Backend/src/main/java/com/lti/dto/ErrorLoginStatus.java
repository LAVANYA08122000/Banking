package com.lti.dto;

import java.util.List;

import com.lti.entity.ErrorLogin;

public class ErrorLoginStatus extends Status{
	private List<String> errorLogins;

	public List<String> getErrorLogins() {
		return errorLogins;
	}

	public void setErrorLogins(List<String> errorLogins) {
		this.errorLogins = errorLogins;
	}
}
