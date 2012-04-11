package com.xfashion.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateBrandPopup {
	
	private DialogBox popup = null;
	
	private TextBox brandTextBox = null;
	
	private PanelMediator panelMediator = null;

	public CreateBrandPopup(PanelMediator panelMediator) {
		this.panelMediator = panelMediator;
		panelMediator.setCreateBrandPopup(this);
	}
	
	public void showForCategory() {
		if (popup == null) {
			popup = createPopup();
		}
		brandTextBox.setText("");
		popup.show();
		brandTextBox.setFocus(true);
	}
	
	private DialogBox createPopup() {
		// Caption popupCaption = new Caption
		final DialogBox popup = new DialogBox();

		popup.setGlassEnabled(true);
		popup.setAnimationEnabled(true);
		VerticalPanel panel = new VerticalPanel();
		
		Label headerLabel = new Label("Marke anlegen");
		panel.add(headerLabel);
		
		HorizontalPanel styleNamePanel = new HorizontalPanel();
		Label styleLabel = new Label("Marke: ");
		styleNamePanel.add(styleLabel);
		brandTextBox = new TextBox();
		styleNamePanel.add(brandTextBox);
		panel.add(styleNamePanel);
		
		HorizontalPanel navPanel = new HorizontalPanel();
		Button createButton = new Button("Anlegen");
		createButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(brandTextBox.getText() != null && brandTextBox.getText().length() > 0) {
					panelMediator.addBrand(brandTextBox.getText());
				}
				popup.hide();
			}
		});
		navPanel.add(createButton);
		Button cancelButton = new Button("Abbrechen");
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				popup.hide();
			}
		});
		navPanel.add(cancelButton);
		panel.add(navPanel);
		
		popup.add(panel);
		popup.center();
		
		return popup;
	}

	public PanelMediator getPanelMediator() {
		return panelMediator;
	}

}
