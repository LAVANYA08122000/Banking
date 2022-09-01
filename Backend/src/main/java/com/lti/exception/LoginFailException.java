package com.lti.exception;

public class LoginFailException extends RuntimeException{
	String message;

	public LoginFailException(String message) {
		super(message);
	}
}
