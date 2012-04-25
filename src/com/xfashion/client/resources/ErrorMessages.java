package com.xfashion.client.resources;

import com.google.gwt.i18n.client.Messages;

public interface ErrorMessages extends Messages {

	String createAttributeNoName();

	String categoryIsNotEmpty(String categoryName);
	String categoryDeleteFailed(String exceptionMessage);
	
	String brandIsNotEmpty(String brandName);
	String brandDeleteFailed(String exceptionMessage);

	String styleIsNotEmpty(String styleName);
	String styleDeleteFailed(String exceptionMessage);

	String sizeIsNotEmpty(String brandName);
	String sizeDeleteFailed(String exceptionMessage);

	String colorIsNotEmpty(String styleName);
	String colorDeleteFailed(String exceptionMessage);

	String articleCreateNoCategory();
	String articleCreateNoBrand();
	String articleCreateNoStyle();
	String articleCreateNoSize();
	String articleCreateNoColor();

	String invalidPrice();
	String invalidName();
}
