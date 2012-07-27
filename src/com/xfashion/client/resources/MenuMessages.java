package com.xfashion.client.resources;

import com.google.gwt.i18n.client.Messages;

public interface MenuMessages extends Messages {

	@DefaultMessage("Eingeloggt als")
	String loggedInAs();
	
	@DefaultMessage("Artikel-Typen")
	String articleType();
	
	@DefaultMessage("User Management")
	String userManagement();
	
	@DefaultMessage("User Profil")
	String userProfile();
	
	@DefaultMessage("Merkliste")
	String notepadManagement();
	
	@DefaultMessage("Test")
	String test();

	@DefaultMessage("Bestand")
	String stock();

	@DefaultMessage("Verkäufe")
	String sellStatistic();

	@DefaultMessage("Aktionen")
	String promo();
	
	@DefaultMessage("VK Preis Änderungen ({0,number})")
	String priceChanges(int priceChangeAmount);

	@DefaultMessage("Verkauf")
	String sellArticle();
	
	@DefaultMessage("Artikel verkaufen mit Barcode-Scan")
	String sellArticleHint();


}
