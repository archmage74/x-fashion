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
import com.xfashion.client.notepad.event.ClearNotepadEvent;
import com.xfashion.client.notepad.event.ClearNotepadHandler;
import com.xfashion.client.notepad.event.NotepadAddArticleEvent;
import com.xfashion.client.notepad.event.NotepadAddArticleHandler;
import com.xfashion.client.notepad.event.NotepadRemoveArticleEvent;
import com.xfashion.client.notepad.event.NotepadRemoveArticleHandler;
import com.xfashion.client.notepad.event.OpenDeliveryNoticeEvent;
import com.xfashion.client.notepad.event.OpenDeliveryNoticeHandler;
import com.xfashion.client.notepad.event.OpenNotepadEvent;
import com.xfashion.client.notepad.event.OpenNotepadHandler;
import com.xfashion.client.notepad.event.PrintDeliveryNoticeEvent;
import com.xfashion.client.notepad.event.PrintDeliveryNoticeHandler;
import com.xfashion.client.notepad.event.PrintNotepadStickersEvent;
import com.xfashion.client.notepad.event.PrintNotepadStickersHandler;
import com.xfashion.client.notepad.event.RecordArticlesEvent;
import com.xfashion.client.notepad.event.RecordArticlesHandler;
import com.xfashion.client.notepad.event.RequestOpenNotepadEvent;
import com.xfashion.client.notepad.event.RequestOpenNotepadHandler;
import com.xfashion.client.notepad.event.RequestSaveNotepadEvent;
import com.xfashion.client.notepad.event.RequestSaveNotepadHandler;
import com.xfashion.client.notepad.event.SaveDeliveryNoticeEvent;
import com.xfashion.client.notepad.event.SaveDeliveryNoticeHandler;
import com.xfashion.client.notepad.event.SaveNotepadEvent;
import com.xfashion.client.notepad.event.SaveNotepadHandler;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.notepad.ArticleAmountDTO;
import com.xfashion.shared.notepad.NotepadDTO;

public class NotepadManagement implements NotepadAddArticleHandler, NotepadRemoveArticleHandler, PrintNotepadStickersHandler, ClearNotepadHandler,
		OpenNotepadHandler, OpenDeliveryNoticeHandler, RequestOpenNotepadHandler, SaveDeliveryNoticeHandler, SaveNotepadHandler, RequestSaveNotepadHandler, PrintDeliveryNoticeHandler, RecordArticlesHandler {

	public static final String PRINT_STICKER_URL = "/pdf/multisticker";

	public static final String PRINT_DELIVERY_NOTICE_URL = "/pdf/deliverynotice";

	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	
	private NotepadServiceAsync notepadService;

	private Panel panel;

	private TextMessages textMessages;
	private ErrorMessages errorMessages;

	private NotepadDTO currentNotepad;
	private DeliveryNoticeDTO currentDeliveryNotice;

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
			sendNotepadPrintAction(createPrintStickerCallback());
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
		if (currentNotepad.getArticles().size() == 0) {
			Xfashion.fireError(errorMessages.noArticlesInNotepad());
		} else if (currentDeliveryNotice == null) {
			Xfashion.fireError(errorMessages.noDeliveryNotice());
		} else {
			sendDeliveryNoticePrintAction(createPrintDeliveryNoticeCallback());
		}
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
	public void onSaveDeliveryNotice(SaveDeliveryNoticeEvent event) {
		currentDeliveryNotice = event.getDeliveryNotice();
		if (currentDeliveryNotice.getKey() == null) {
			createDeliveryNotice(currentDeliveryNotice);
		} else {
			updateDeliveryNotice(currentDeliveryNotice);
		}
	}

	@Override
	public void onRequestOpenNotepad(RequestOpenNotepadEvent event) {
		openNotepadPopup.show(currentNotepad);
	}

	@Override
	public void onOpenNotepad(OpenNotepadEvent event) {
		currentDeliveryNotice = null;
		currentNotepad = event.getNotepad();
		refreshProvider();
	}

	@Override
	public void onOpenDeliveryNotice(OpenDeliveryNoticeEvent event) {
		currentDeliveryNotice = event.getDeliveryNotice();
		currentNotepad = currentDeliveryNotice.getNotepad();
		refreshProvider();
	}

	protected void sendNotepadPrintAction(AsyncCallback<Void> printCallback) {
		notepadService.saveNotepadInSession(currentNotepad, printCallback);
	}
	
	protected void sendDeliveryNoticePrintAction(AsyncCallback<Void> printCallback) {
		notepadService.saveDeliveryNoticeInSession(currentDeliveryNotice, printCallback);
	}
	
	protected AsyncCallback<Void> createPrintStickerCallback() {
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
		return callback;
	}

	protected AsyncCallback<Void> createPrintDeliveryNoticeCallback() {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				Window.open(PRINT_DELIVERY_NOTICE_URL, "_blank", "");
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		return callback;
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

	private void createDeliveryNotice(DeliveryNoticeDTO deliverNotice) {
		AsyncCallback<DeliveryNoticeDTO> callback = new AsyncCallback<DeliveryNoticeDTO>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(DeliveryNoticeDTO result) {
				currentNotepad = result.getNotepad();
				refreshProvider();
			}
		};
		userService.createDeliveryNotice(deliverNotice, callback);
	}
	
	private void updateDeliveryNotice(DeliveryNoticeDTO deliveryNotice) {
		AsyncCallback<DeliveryNoticeDTO> callback = new AsyncCallback<DeliveryNoticeDTO>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(DeliveryNoticeDTO result) {
				currentNotepad = result.getNotepad();
				refreshProvider();
			}
		};
		userService.updateDeliveryNotice(deliveryNotice, callback);
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(NotepadAddArticleEvent.TYPE, this);
		Xfashion.eventBus.addHandler(NotepadRemoveArticleEvent.TYPE, this);
		Xfashion.eventBus.addHandler(PrintNotepadStickersEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ClearNotepadEvent.TYPE, this);
		Xfashion.eventBus.addHandler(OpenNotepadEvent.TYPE, this);
		Xfashion.eventBus.addHandler(OpenDeliveryNoticeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(SaveNotepadEvent.TYPE, this);
		Xfashion.eventBus.addHandler(SaveDeliveryNoticeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestSaveNotepadEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RecordArticlesEvent.TYPE, this);
		Xfashion.eventBus.addHandler(PrintDeliveryNoticeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestOpenNotepadEvent.TYPE, this);
	}

}
