package com.xfashion.client.notepad;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.notepad.ArticleAmountDTO;
import com.xfashion.shared.notepad.NotepadDTO;

public class NotepadManagement implements NotepadAddArticleHandler, NotepadRemoveArticleHandler, PrintNotepadStickersHandler, ClearNotepadHandler,
		OpenNotepadHandler, RequestOpenNotepadHandler, SaveNotepadHandler, RequestSaveNotepadHandler, PrintDeliveryNoticeHandler, RecordArticlesHandler {

	public static final String PRINT_STICKER_URL = "/pdf/multisticker";

	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	
	private NotepadServiceAsync notepadService;

	private Panel panel;

	private TextMessages textMessages;
	private ErrorMessages errorMessages;

	private NotepadDTO currentNotepad;

	private ListBox articleListBox;

	private ArticleAmountDataProvider articleProvider;

	private ArticleTypeDatabase articleTypeDatabase;
	
	protected SaveNotepadPopup saveNotepadPopup;
	protected OpenNotepadPopup openNotepadPopup;

	public NotepadManagement(ArticleTypeDatabase articleTypeDatabase) {
		this.articleTypeDatabase = articleTypeDatabase;
		articleProvider = new ArticleAmountDataProvider(articleTypeDatabase);
		textMessages = GWT.create(TextMessages.class);
		errorMessages = GWT.create(ErrorMessages.class);
		notepadService = (NotepadServiceAsync) GWT.create(NotepadService.class);
		currentNotepad = new NotepadDTO();
		saveNotepadPopup = new SaveNotepadPopup();
		openNotepadPopup = new OpenNotepadPopup();
		registerForEvents();
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
		return buttonPanel;
	}

	@Override
	public void onPrintNotepadStickers(PrintNotepadStickersEvent event) {
		if (currentNotepad.getArticles().size() > 0) {
			sendNotepad();
		} else {
			Xfashion.fireError(errorMessages.noArticlesInNotepad());
		}
	}

	@Override
	public void onClearNotepad(ClearNotepadEvent event) {
		resetNotepad();
	}

	private void resetNotepad() {
		currentNotepad.getArticles().clear();
		currentNotepad.setCreationDate(new Date());
		refreshProvider();
	}
	
	private void refreshProvider() {
		articleProvider.setList(currentNotepad.getArticles());
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
		if (articleListBox != null) {
			articleListBox.clear();
			for (ArticleAmountDTO aa : currentNotepad.getArticles()) {
				StringBuffer item = new StringBuffer();
				item.append(aa.getAmount());
				item.append(" - ");
				item.append(articleTypeDatabase.getArticleTypeProvider().resolveData(aa.getArticleTypeKey()).getName());
				articleListBox.addItem(item.toString());
			}
		}
	}

	@Override
	public void onNotepadAddArticle(NotepadAddArticleEvent event) {
		currentNotepad.addArticle(event.getArticleType(), event.getAmount());
		articleProvider.setList(currentNotepad.getArticles());
		refreshListBox();
	}

	@Override
	public void onNotepadRemoveArticle(NotepadRemoveArticleEvent event) {
		currentNotepad.deductArticleType(event.getArticleType(), event.getAmount());
		articleProvider.setList(currentNotepad.getArticles());
		refreshListBox();
	}


	@Override
	public void onRecordArticles(RecordArticlesEvent event) {
		Xfashion.fireError(errorMessages.notImplemented());
	}

	@Override
	public void onPrintDeliveryNotice(PrintDeliveryNoticeEvent event) {
		Xfashion.fireError(errorMessages.notImplemented());
	}

	@Override
	public void onRequestSaveNotepad(RequestSaveNotepadEvent event) {
		saveNotepadPopup.show(currentNotepad);
	}

	@Override
	public void onSaveNotepad(SaveNotepadEvent event) {
		NotepadDTO notepad = event.getNotepad();
		if (notepad.getKey() == null) {
			createNotepad(notepad);
		} else {
			updateNotepad(notepad);
		}
	}

	@Override
	public void onRequestOpenNotepad(RequestOpenNotepadEvent event) {
		openNotepadPopup.show(currentNotepad);
	}

	@Override
	public void onOpenNotepad(OpenNotepadEvent event) {
		currentNotepad = event.getNotepad();
		refreshProvider();
//		AsyncCallback<Set<NotepadDTO>> callback = new AsyncCallback<Set<NotepadDTO>>() {
//			@Override
//			public void onSuccess(Set<NotepadDTO> result) {
//				if (result.size() > 0) {
//				} else {
//					Xfashion.fireError("no notepads founds");
//				}
//			}
//			@Override
//			public void onFailure(Throwable caught) {
//				Xfashion.fireError(caught.getMessage());
//			}
//		};
//		userService.readOwnNotepads(callback);
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
		notepadService.saveNotepadInSession(currentNotepad, callback);
	}

	public ArticleAmountDataProvider getArticleProvider() {
		return articleProvider;
	}

	public void setArticleProvider(ArticleAmountDataProvider articleProvider) {
		this.articleProvider = articleProvider;
	}

	private void createNotepad(NotepadDTO notepad) {
		AsyncCallback<NotepadDTO> callback = new AsyncCallback<NotepadDTO>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(NotepadDTO result) {
				currentNotepad = result;
				refreshProvider();
			}
		};
		userService.createNotepad(notepad, callback);
	}
	
	private void updateNotepad(NotepadDTO notepad) {
		AsyncCallback<NotepadDTO> callback = new AsyncCallback<NotepadDTO>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(NotepadDTO result) {
				currentNotepad = result;
				refreshProvider();
			}
		};
		userService.updateOwnNotepad(notepad, callback);
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(NotepadAddArticleEvent.TYPE, this);
		Xfashion.eventBus.addHandler(NotepadRemoveArticleEvent.TYPE, this);
		Xfashion.eventBus.addHandler(PrintNotepadStickersEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ClearNotepadEvent.TYPE, this);
		Xfashion.eventBus.addHandler(OpenNotepadEvent.TYPE, this);
		Xfashion.eventBus.addHandler(SaveNotepadEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestSaveNotepadEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RecordArticlesEvent.TYPE, this);
		Xfashion.eventBus.addHandler(PrintDeliveryNoticeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestOpenNotepadEvent.TYPE, this);
	}

}
