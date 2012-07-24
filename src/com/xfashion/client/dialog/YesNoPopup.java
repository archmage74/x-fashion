package com.xfashion.client.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.xfashion.client.resources.TextMessages;

public class YesNoPopup {

	protected String question;
	protected String yes;
	protected String no;
	protected YesNoCallback callback;
	protected String caption;
	
	protected DialogBox dialogBox;
	
	protected TextMessages textMessages; 
	
	public YesNoPopup(String question, YesNoCallback callback) {
		this.textMessages = GWT.create(TextMessages.class);
		this.question = question;
		this.yes = textMessages.ok();
		this.no = textMessages.cancel();
		this.callback = callback;
	}

	public String getYes() {
		return yes;
	}

	public void setYes(String yes) {
		this.yes = yes;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void show() {
		createDialogBox();
		dialogBox.setPopupPosition(500, 50);
		dialogBox.show();
	}

	public DialogBox createDialogBox() {
		dialogBox = new DialogBox();
		if (caption != null) {
			dialogBox.setText(caption);
		}
			
		DockPanel dp = new DockPanel();
		
		Label questionLabel = new Label(question);
		dp.add(questionLabel, DockPanel.NORTH);
		dp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		dp.add(createYesButton(), DockPanel.WEST);
		dp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		dp.add(createNoButton(), DockPanel.EAST);
		dialogBox.add(dp);

		return dialogBox;
	}

	private Button createYesButton() {
		Button yesButton = new Button(yes);
		yesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				callback.yes();
			}
		});
		return yesButton;
	}
	
	private Button createNoButton() {
		Button noButton = new Button(no);
		noButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				callback.no();
			}
		});
		return noButton;
	}

}
