package com.lti.exception;

public class ServiceException extends RuntimeException {

	String message;

	public ServiceException(String message) {
		super(message);
	}

}
