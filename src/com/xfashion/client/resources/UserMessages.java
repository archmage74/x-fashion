package com.xfashion.client.resources;

import com.google.gwt.i18n.client.Messages;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface UserMessages extends Messages {

	@DefaultMessage("User Management")
	String userManagementHeader();
	
	@DefaultMessage("User-Name")
	String username();
	
	@DefaultMessage("Passwort")
	String password();
	
	@DefaultMessage("Shop-Kürzel")
	String shortName();

	@DefaultMessage("Shop-Name")
	String shopName();

	@DefaultMessage("Strasse/HNr.")
	String streetAndHousenumber();

	@DefaultMessage("PLZ/Ort")
	String postalcodeAndCity();

	@DefaultMessage("E-Mail")
	String email();
	
	@DefaultMessage("Aktiviert")
	String enabled();

	@DefaultMessage("Speichern")
	String save();
	
	@DefaultMessage("User anlegen")
	String createUser();
	
	@DefaultMessage("Passwort-Ändern-Link")
	SafeHtml sendPassword();

	@DefaultMessage("Generiert einen Passwort-Ändern-Link und sendet diesen per E-Mail and den Benutzer.")
	String sendPasswordHint();

	@DefaultMessage("Ein Passwort-Ändern-Link wurde erstellt und versendet.")
	String passwordSent();
	
	
	
	@DefaultMessage("Name darf nicht leer sein.")
	String errorUsernameEmpty();
	
	@DefaultMessage("Kein Benutzer ausgewählt.")
	String errorNoUserSelected();

	@DefaultMessage("E-Mail-Adresse darf nicht leer sein.")
	String errorEmailEmpty();

	@DefaultMessage("Login")
	String userLoginHeader();

	@DefaultMessage("User Profil")
	String userProfileHeader();
	
	@DefaultMessage("Login")
	String login();
	
	@DefaultMessage("Name und/oder Passwort falsch.")
	String loginFailed();

	@DefaultMessage("Land:")
	String country();

	@DefaultMessage("Rolle:")
	String role();

}
