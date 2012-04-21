package com.xfashion.client.resources;

import com.google.gwt.i18n.client.Messages;

public interface ErrorMessages extends Messages {

	String categoryDeleteFailed(String exceptionMessage);
	String categoryIsNotEmpty(String categoryName);
	String categoryCreateNoName();
	
	String articleCreateNoCategory();
	String articleCreateNoBrand();
	String articleCreateNoStyle();
	String articleCreateNoSize();
	String articleCreateNoColor();

}
