package com.xfashion.shared;

public class EanFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EanFormatException() {
		super();
	}
	
	public EanFormatException(Throwable t) {
		super(t);
	}
	
	public EanFormatException(String msg) {
		super(msg);
	}
	
	public EanFormatException(String msg, Throwable t) {
		super(msg, t);
	}
	
}
