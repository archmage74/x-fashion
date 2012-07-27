package com.xfashion.client.resources;

import com.google.gwt.i18n.client.Messages;

public interface ErrorMessages extends Messages {

	String notImplemented();
	
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

	String createUserNoUserManagement();
	
	String noArticlesInNotepad();

	String noCategorySelected();

	String noNotepadNameSpecified();
	String noNotepadToOpenSelected();
	String notepadNotEmpty();
	String notepadEmpty();
	String noDeliveryNotice();
	String noValidDeliveryNoticeEAN();
	String noValidArticleTypeEAN();
	String cannotAddDeliveryNoticeToStock();

	String notEnoughArticlesInStock();

	String noSellArticles();
	String unknownPromo();
	String onlyOnePromoAllowed();
	String bothPromoValueSpecified();


}
