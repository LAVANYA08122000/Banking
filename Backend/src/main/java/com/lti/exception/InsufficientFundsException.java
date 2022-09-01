package com.lti.exception;

public class InsufficientFundsException extends Exception {
	String message;

	public InsufficientFundsException(String message) {
		super(message);
	}

}
