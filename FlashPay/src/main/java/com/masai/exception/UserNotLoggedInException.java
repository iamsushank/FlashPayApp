package com.masai.exception;

public class UserNotLoggedInException extends Exception {

	
	public UserNotLoggedInException() {}

	public UserNotLoggedInException(String message) {
		super(message);
	}
	
}
