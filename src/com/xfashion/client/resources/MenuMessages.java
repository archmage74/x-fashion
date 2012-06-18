package com.xfashion.client.resources;

import com.google.gwt.i18n.client.Messages;
import com.google.gwt.safehtml.shared.SafeHtml;

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
	SafeHtml stock();

	@DefaultMessage("Verkäufe")
	SafeHtml sellStatistic();
}
