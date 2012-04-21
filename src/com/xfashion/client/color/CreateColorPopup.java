package com.xfashion.client.color;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.CreatePopup;
import com.xfashion.client.PanelMediator;

public class CreateColorPopup extends CreatePopup {
	
	private DialogBox popup = null;
	
	private TextBox nameTextBox = null;
	
	public CreateColorPopup(PanelMediator panelMediator) {
		super(panelMediator);
		panelMediator.setCreateColorPopup(this);
	}
	
	public void show() {
		if (popup == null) {
			popup = createPopup();
		}
		nameTextBox.setText("");
		popup.show();
		nameTextBox.setFocus(true);
	}
	
	private DialogBox createPopup() {
		// Caption popupCaption = new Caption
		final DialogBox popup = new DialogBox();

		popup.setGlassEnabled(true);
		popup.setAnimationEnabled(true);
		VerticalPanel panel = new VerticalPanel();
		
		Label headerLabel = new Label("Farbe anlegen");
		panel.add(headerLabel);
		
		HorizontalPanel namePanel = new HorizontalPanel();
		Label nameLabel = new Label("Farbe: ");
		namePanel.add(nameLabel);
		nameTextBox = new TextBox();
		namePanel.add(nameTextBox);
		panel.add(namePanel);
		
		HorizontalPanel navPanel = new HorizontalPanel();
		Button createButton = new Button("Anlegen");
		createButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(nameTextBox.getText() != null && nameTextBox.getText().length() > 0) {
					getPanelMediator().addColor(nameTextBox.getText());
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

}
