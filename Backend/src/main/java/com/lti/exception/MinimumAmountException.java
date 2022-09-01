package com.lti.exception;

public class MinimumAmountException extends Exception {

	String message;

	public MinimumAmountException(String message) {
		super(message);
	}

}
