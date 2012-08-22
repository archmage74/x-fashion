package com.xfashion.client.notepad;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.event.ContentPanelResizeEvent;
import com.xfashion.client.event.ContentPanelResizeHandler;
import com.xfashion.client.resources.TextMessages;

public class NotepadPopup implements ContentPanelResizeHandler {

	// public static final int WIDTH = 360;
	public static final int MINIMIZED_HEIGHT = 30;
	public static final int POPUP_Y = 350;
	
	protected DialogBox dialogBox;
	protected Panel panel;
	protected NotepadPanel notepadPanel;

	private IProvideArticleFilter filterProvider;
	private ArticleAmountDataProvider notepadArticleProvider;
	
	protected DialogBox minimized;
	
	protected TextMessages textMessages = GWT.create(TextMessages.class);
	
	public NotepadPopup(ArticleAmountDataProvider notepadArticleProvider, IProvideArticleFilter filterProvider) {
		this.notepadArticleProvider = notepadArticleProvider;
		this.filterProvider = filterProvider;
		registerForEvents();
	}
	
	public void show() {
		hideMinimized();
		if (dialogBox == null) {
			createNotepadDialogBox();
		}
		dialogBox.setPopupPosition(-14, POPUP_Y);
		positionPopup();
		dialogBox.show();
	}
	
	public void hide() {
		if (dialogBox != null && dialogBox.isShowing()) {
			dialogBox.hide();
		}
	}
	
	public void showMinimized() {
		hide();
		if (minimized == null) {
			createMinimizedDialogBox();
		}
		positionMinimized();
		minimized.show();
	}
	
	public void hideMinimized() {
		if (minimized != null && minimized.isShowing()) {
			minimized.hide();
		}
	}
	
	@Override
	public void onContentPanelResize(ContentPanelResizeEvent event) {
		positionMinimized();
		positionPopup();
	}
	
	protected void positionMinimized() {
		if (minimized != null) {
			minimized.setPopupPosition(0, Window.getClientHeight() - MINIMIZED_HEIGHT);
		}
	}
	
	protected void positionPopup() {
		if (notepadPanel != null && dialogBox != null) {
			notepadPanel.setHeight(Window.getClientHeight() - POPUP_Y - 30); 
		}
	}

	protected DialogBox createMinimizedDialogBox() {
		minimized = new DialogBox(false, false);
		minimized.setStyleName("notepadMinimized");
		HorizontalPanel panel = new HorizontalPanel();
		panel.setStyleName("notepadMinimized");
		minimized.add(panel);

		Label label = new Label(textMessages.notepadManagementHeader());
		label.setStyleName("notepadMinimized");
		label.setWidth(NotepadPanel.PANEL_MAX_WIDTH + "px");
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				show();
			}
		});
		panel.add(label);
		
		return minimized;
	}
	
	protected DialogBox createNotepadDialogBox() {
		dialogBox = new DialogBox(false, false);
		
		notepadPanel = new NotepadPanel(filterProvider);
		panel = notepadPanel.createPanel(notepadArticleProvider);
		dialogBox.add(panel);
		return dialogBox;
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(ContentPanelResizeEvent.TYPE, this);
	}
}
