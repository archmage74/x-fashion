package com.xfashion.client.resources;

import com.google.gwt.i18n.client.Messages;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface UserMessages extends Messages {

	@DefaultMessage("User Mangement")
	String userManagementHeader();
	
	@DefaultMessage("Name")
	String username();
	
	@DefaultMessage("Beschreibung")
	String description();
	
	@DefaultMessage("E-Mail")
	String email();
	
	@DefaultMessage("Aktiviert")
	String enabled();

	@DefaultMessage("Speichern")
	String save();
	
	@DefaultMessage("User anlegen")
	String createUser();
	
	@DefaultMessage("Passwort senden")
	SafeHtml sendPassword();

	@DefaultMessage("Generiert ein neues passwort und sendet es an die angegebene E-Mail Adresse.")
	String sendPasswordHint();

	@DefaultMessage("Ein neues Passwort wurde erstellt und versendet.")
	String passwordSent();
	
	
	
	@DefaultMessage("Name darf nicht leer sein.")
	String errorUsernameEmpty();
	
	@DefaultMessage("Kein Benutzer ausgewählt.")
	String errorNoUserSelected();

	@DefaultMessage("E-Mail-Adresse darf nicht leer sein.")
	String errorEmailEmpty();


}
