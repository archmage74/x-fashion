package com.xfashion.server.user;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 242986741979923085L;

	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}
	
	public UserNotFoundException(Throwable t) {
		super(t);
	}
	
	public UserNotFoundException(String message, Throwable t) {
		super(message, t);
	}
	
}
