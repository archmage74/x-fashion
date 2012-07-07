package com.xfashion.client.resources;

import java.util.Date;

import com.google.gwt.i18n.client.Messages;

public interface TextMessages extends Messages {

	String deleteArticleTypeButton();
	String confirmDeleteArticle();
	String addOneToNotepadButton();
	String addTenToNotepadButton();
	String removeOneFromNotepadButton();
	String removeTenFromNotepadButton();
	
	String articleCreateMultipleSizes();
	String articleCreateHeader();
	String articleCreate();
	String selectImage();
	String select();
	
	String bulkMultipleAttributes();
	String bulkEdit();
	String bulkEditConfirmation(int articleNumber);
	
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
	String image();
	
	String currencySign();
	String yes();
	String no();
	String save();
	String cancel();
	String edit();
	String close();
	String open();

	String notepadManagementHeader();
	String printSticker();
	String clearNotepad();
	String registerArticles();
	String notepadToStock();
	String loadNotepad();
	String retrieveNotepad();
	String saveNotepad();
	String orderNotepad();
	String notepadTypeNotepad();
	String notepadTypeDeliveryNotice();
	String notepadInfo1(String notepadName);
	String notepadInfo2(Date creationDate);
	String deliveryNoticeInfo1(String deliveryNoticeEan); 
	String deliveryNoticeInfo2(String shopName, Date creationDate);
	
	String createArticle();
	String clearNameSuggestFilter();

	String deliveryNoticeListBoxLine(String deliveryNoticeEan, String shopName, Date creationDate);
	String notepadListBoxLine(String notepadName, Date creationDate);
	String clearNotepadHint();
	String openNotepadHint();
	String saveNotepadHint();
	String printDeliveryNoticeHint();
	String printStickersHint();
	String recordArticlesHint();
	String intoStockHint();
	String sellHint();

	String stockManagementHeader();
	String intoStockResult(Long addedArticleNumber);
	String sellStockResult(Long soldArticleNumber);
	String sellArticles();
	String sellStatisticHeader();
	String allShops();
	String sellStatisticDate(Date sellDate);
	String addMoreSoldArticles();
}
