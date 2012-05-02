package com.xfashion.client.notepad;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.AddArticleEvent;
import com.xfashion.client.at.AddArticleHandler;
import com.xfashion.client.at.ArticleTypeDatabase;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.notepad.NotepadDTO;

public class NotepadManagement implements AddArticleHandler {
	
	public static final String PRINT_STICKER_URL = "/pdf/multisticker";
	
	private NotepadServiceAsync notepadService;
	
	private Panel panel;
	
	private TextMessages textMessages;
	
	private NotepadDTO currentNotepad;
	
	private ListBox articleListBox;
	
	private ArticleTypeDatabase articleTypeDatabase;
	
	public NotepadManagement(ArticleTypeDatabase articleTypeDatabase) {
		this.articleTypeDatabase = articleTypeDatabase; 
		textMessages = GWT.create(TextMessages.class);
		notepadService = (NotepadServiceAsync) GWT.create(NotepadService.class);
		currentNotepad = new NotepadDTO();
		Xfashion.eventBus.addHandler(AddArticleEvent.TYPE, this);
	}
	
	public Panel getPanel() {
		if (panel == null) {
			panel = createPanel();
		}
		return panel;
	}
	
	public Panel createPanel() {
		
		VerticalPanel vp = new VerticalPanel();
		
		Label l = createHeader();
		vp.add(l);

		articleListBox = createArticleListBox();
		vp.add(articleListBox);
		
		Button printStickerButton = new Button(textMessages.printSticker());
		printStickerButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendNotepad();
			}
		});
		vp.add(printStickerButton);
		
		return vp;
	}

	private Label createHeader() {
		Label l = new Label(textMessages.notepadManagementHeader());
		l.setStyleName("contentHeader");
		return l;
	}
	
	private ListBox createArticleListBox() {
		ListBox lb = new ListBox();
		lb.setWidth("200px");
		lb.setVisibleItemCount(30);
		for (Long productNumber : currentNotepad.getArticleTypes()) {
			lb.addItem(articleTypeDatabase.getArticleTypeProvider().resolveData(productNumber).getName());
		}
		return lb;
	}

	@Override
	public void onAddArticle(AddArticleEvent event) {
		currentNotepad.addArticleType(event.getArticleType());
		if (articleListBox != null) {
			articleListBox.addItem(event.getArticleType().getName());
		}
	}
	
	protected void sendNotepad() {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				Window.open(PRINT_STICKER_URL, "_blank", "");
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		notepadService.sendNotepad(currentNotepad, callback);
	}
}
