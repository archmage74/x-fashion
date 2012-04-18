package com.xfashion.client;

public class CreateArticleException extends Exception {

	private static final long serialVersionUID = 1L;

	public CreateArticleException() {
		super();
	}

	public CreateArticleException(String msg) {
		super(msg);
	}
	
	public CreateArticleException(Throwable t) {
		super(t);
	}
	
	public CreateArticleException(String msg, Throwable t) {
		super(msg, t);
	}
}
