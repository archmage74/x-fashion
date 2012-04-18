package com.xfashion.client;

import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Label;

public class ErrorPopup {

	DecoratedPopupPanel popup;
	
	Label errorLabel;
	
	public ErrorPopup() {
	}
	
	public void showPopup(String errorMessage) {
		if (popup == null) {
			popup = createPopup();
		}
		errorLabel.setText(errorMessage);
		popup.center();
	}

	public DecoratedPopupPanel createPopup() {
		DecoratedPopupPanel popup = new DecoratedPopupPanel(true);
		popup.setGlassEnabled(true);
		popup.setAnimationEnabled(true);
		errorLabel = new Label("");
		errorLabel.addStyleName("errorText");
		popup.add(errorLabel);
		return popup;
	}
	
}
