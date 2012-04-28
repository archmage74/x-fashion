package com.xfashion.client.resources;

import com.google.gwt.i18n.client.Messages;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface TextMessages extends Messages {

	SafeHtml deleteArticleTypeButton();
	
	String name();
	String brand();
	String style();
	String size();
	String color();
	String category();
	String buyPrice();
	String sellPrice();
	String ean();
	String sticker();
	
	String confirmDeleteArticle();
	String yes();
	String no();
	
}
