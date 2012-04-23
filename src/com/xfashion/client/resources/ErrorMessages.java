package com.xfashion.client.resources;

import com.google.gwt.i18n.client.Messages;

public interface ErrorMessages extends Messages {

	String categoryCreateNoName();
	String categoryIsNotEmpty(String categoryName);
	String categoryDeleteFailed(String exceptionMessage);
	
	String articleCreateNoCategory();
	String articleCreateNoBrand();
	String articleCreateNoStyle();
	String articleCreateNoSize();
	String articleCreateNoColor();

	String brandCreateNoName();
	String brandIsNotEmpty(String brandName);
	String brandDeleteFailed(String exceptionMessage);

	String styleCreateNoName();
	String styleIsNotEmpty(String styleName);
	String styleDeleteFailed(String exceptionMessage);

}
