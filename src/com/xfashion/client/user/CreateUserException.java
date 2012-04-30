package com.xfashion.client.user;

public class CreateUserException extends Exception {

	private static final long serialVersionUID = 1L;

	public CreateUserException() {
		super();
	}

	public CreateUserException(String msg) {
		super(msg);
	}
	
	public CreateUserException(Throwable t) {
		super(t);
	}
	
	public CreateUserException(String msg, Throwable t) {
		super(msg, t);
	}
}
