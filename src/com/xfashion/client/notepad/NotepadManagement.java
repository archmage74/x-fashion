package com.xfashion.client.notepad;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.AddArticleEvent;
import com.xfashion.client.at.AddArticleHandler;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.notepad.NotepadDTO;

public class NotepadManagement implements AddArticleHandler {
	
	public static final String PRINT_STICKER_URL = "/pdf/multisticker";
	
	private NotepadServiceAsync notepadService;
	
	private Panel panel;
	
	private TextMessages textMessages;
	private ErrorMessages errorMessages;
	
	private NotepadDTO currentNotepad;
	
	private ListBox articleListBox;
	
	private ArticleTypeDataProvider articleTypeProvider;
	
	private ArticleTypeDatabase articleTypeDatabase;
	
	public NotepadManagement(ArticleTypeDatabase articleTypeDatabase) {
		this.articleTypeDatabase = articleTypeDatabase;
		articleTypeProvider = new ArticleTypeDataProvider();
		textMessages = GWT.create(TextMessages.class);
		errorMessages = GWT.create(ErrorMessages.class);
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
		
		HorizontalPanel hp = new HorizontalPanel();

		articleListBox = createArticleListBox();
		refreshListBox();
		hp.add(articleListBox);
		
		VerticalPanel buttonPanel = createButtonPanel();
		
		hp.add(buttonPanel);
		vp.add(hp);
		
		return vp;
	}

	private VerticalPanel createButtonPanel() {
		VerticalPanel buttonPanel = new VerticalPanel();

		Button registerArticlesButton = new Button(textMessages.registerArticles());
		registerArticlesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//TODO
				Xfashion.fireError(errorMessages.noteImplemented());
			}
		});
		buttonPanel.add(registerArticlesButton);

		Button loadNotepadButton = new Button(textMessages.loadNotepad());
		loadNotepadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//TODO
				Xfashion.fireError(errorMessages.noteImplemented());
			}
		});
		buttonPanel.add(loadNotepadButton);

		Button retrieveNotepadButton = new Button(textMessages.retrieveNotepad());
		retrieveNotepadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//TODO
				Xfashion.fireError(errorMessages.noteImplemented());
			}
		});
		buttonPanel.add(retrieveNotepadButton);

		Button saveNotepadButton = new Button(textMessages.saveNotepad());
		saveNotepadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//TODO
				Xfashion.fireError(errorMessages.noteImplemented());
			}
		});
		buttonPanel.add(saveNotepadButton);

		Button orderNotepadButton = new Button(textMessages.orderNotepad());
		orderNotepadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				orderNotepad();
			}
		});
		buttonPanel.add(orderNotepadButton);

		Button printStickerButton = new Button(textMessages.printSticker());
		printStickerButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (currentNotepad.getArticleTypes().size() > 0 ) {
					sendNotepad();
				} else {
					Xfashion.fireError(errorMessages.noArticlesInNotepad());
				}
			}
		});
		buttonPanel.add(printStickerButton);

		Button notepadToStockButton = new Button(textMessages.notepadToStock());
		notepadToStockButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//TODO
				Xfashion.fireError(errorMessages.noteImplemented());
			}
		});
		buttonPanel.add(notepadToStockButton);

		Button clearNotepadButton = new Button(textMessages.clearNotepad());
		clearNotepadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				currentNotepad.getArticleTypes().clear();
				refreshListBox();
			}
		});
		buttonPanel.add(clearNotepadButton);

		return buttonPanel;
	}

	private Label createHeader() {
		Label l = new Label(textMessages.notepadManagementHeader());
		l.setStyleName("contentHeader");
		return l;
	}
	
	private ListBox createArticleListBox() {
		ListBox lb = new ListBox();
		lb.setWidth("300px");
		lb.setVisibleItemCount(30);
		return lb;
	}

	private void refreshListBox() {
		articleListBox.clear();
		for (Long productNumber : currentNotepad.getArticleTypes()) {
			articleListBox.addItem(articleTypeDatabase.getArticleTypeProvider().resolveData(productNumber).getName());
		}
	}

	@Override
	public void onAddArticle(AddArticleEvent event) {
		currentNotepad.addArticleType(event.getArticleType());
		articleTypeProvider.getList().add(event.getArticleType());
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
	
	protected void orderNotepad() {
		Collections.sort(currentNotepad.getArticleTypes());
		ArrayList<ArticleTypeDTO> al = new ArrayList<ArticleTypeDTO>();
		for (Long productNumber : currentNotepad.getArticleTypes()) {
			al.add(articleTypeDatabase.getArticleTypeProvider().resolveData(productNumber));
		}
		articleTypeProvider.setList(al);
		refreshListBox();
	}

	public ArticleTypeDataProvider getArticleTypeProvider() {
		return articleTypeProvider;
	}

	public void setArticleTypeProvider(ArticleTypeDataProvider articleTypeProvider) {
		this.articleTypeProvider = articleTypeProvider;
	}
	
}
