package com.xfashion.client;

import com.google.web.bindery.event.shared.Event;

public class ErrorEvent extends Event<ErrorHandler> {

	public static Type<ErrorHandler> TYPE = new Type<ErrorHandler>();
	
	private String errorMessage; 
	
	public ErrorEvent(String errorMessage) {
		setErrorMessage(errorMessage);
	}
	
	@Override
	public Type<ErrorHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(ErrorHandler handler) {
		handler.onError(this);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
