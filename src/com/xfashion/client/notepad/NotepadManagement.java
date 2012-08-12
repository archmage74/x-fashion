package com.xfashion.client.notepad;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.bulk.UpdateArticleTypesEvent;
import com.xfashion.client.at.price.GetAtPriceFromArticleTypeStrategy;
import com.xfashion.client.at.price.GetDePriceFromArticleTypeStrategy;
import com.xfashion.client.notepad.event.ClearNotepadEvent;
import com.xfashion.client.notepad.event.ClearNotepadHandler;
import com.xfashion.client.notepad.event.DeliveryNoticeUpdatedEvent;
import com.xfashion.client.notepad.event.IntoStockEvent;
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
import com.xfashion.client.notepad.event.RequestCheckNotepadPositionEvent;
import com.xfashion.client.notepad.event.RequestIntoStockEvent;
import com.xfashion.client.notepad.event.RequestIntoStockHandler;
import com.xfashion.client.notepad.event.RequestOpenNotepadEvent;
import com.xfashion.client.notepad.event.RequestOpenNotepadHandler;
import com.xfashion.client.notepad.event.RequestSaveNotepadEvent;
import com.xfashion.client.notepad.event.RequestSaveNotepadHandler;
import com.xfashion.client.notepad.event.SaveDeliveryNoticeEvent;
import com.xfashion.client.notepad.event.SaveDeliveryNoticeHandler;
import com.xfashion.client.notepad.event.SaveNotepadEvent;
import com.xfashion.client.notepad.event.SaveNotepadHandler;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.user.UserManagement;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.NotepadDTO;
import com.xfashion.shared.UserCountry;

public class NotepadManagement implements NotepadAddArticleHandler, NotepadRemoveArticleHandler, PrintNotepadStickersHandler, ClearNotepadHandler,
		OpenNotepadHandler, OpenDeliveryNoticeHandler, RequestOpenNotepadHandler, SaveDeliveryNoticeHandler, SaveNotepadHandler,
		RequestSaveNotepadHandler, PrintDeliveryNoticeHandler, RecordArticlesHandler, RequestIntoStockHandler {

	public GetPriceFromArticleAmountStrategy<ArticleAmountDTO> atPriceStrategy = null;
	public GetPriceFromArticleAmountStrategy<ArticleAmountDTO> dePriceStrategy = null;

	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);

	private ErrorMessages errorMessages;

	private NotepadDTO currentNotepad;
	private DeliveryNoticeDTO currentDeliveryNotice;

	private ArticleAmountDataProvider articleProvider;
	protected NotepadPrinter notepadPrinter;

	protected SaveNotepadPopup saveNotepadPopup;
	protected OpenNotepadPopup openNotepadPopup;
	protected ScanArticlePopup scanArticlePopup;

	private static NotepadManagement notepadManagement;

	public static NotepadManagement getInstance() {
		if (notepadManagement == null) {
			throw new RuntimeException("NotepadManagement is not yet initialized");
		}
		return notepadManagement;
	}
	
	public static NotepadManagement getInstance(ArticleTypeDataProvider articleTypeDataProvider) {
		if (notepadManagement == null) {
			notepadManagement = new NotepadManagement(articleTypeDataProvider);
		}
		return notepadManagement;
	}

	protected NotepadManagement(ArticleTypeDataProvider articleTypeProvider) {
		this.articleProvider = new ArticleAmountDataProvider(articleTypeProvider);
		this.notepadPrinter = new NotepadPrinter();
		this.errorMessages = GWT.create(ErrorMessages.class);
		this.currentNotepad = new NotepadDTO();
		this.saveNotepadPopup = new SaveNotepadPopup();
		this.openNotepadPopup = new OpenNotepadPopup();
		this.scanArticlePopup = new ScanArticlePopup(articleProvider);
		this.atPriceStrategy = new GetPriceFromArticleAmountStrategy<ArticleAmountDTO>(this.articleProvider, new GetAtPriceFromArticleTypeStrategy());
		this.dePriceStrategy = new GetPriceFromArticleAmountStrategy<ArticleAmountDTO>(this.articleProvider, new GetDePriceFromArticleTypeStrategy());
		registerForEvents();
	}

	public GetPriceFromArticleAmountStrategy<ArticleAmountDTO> currentPriceStrategy() {
		UserCountry country = UserManagement.user.getCountry();
		if (currentDeliveryNotice != null) {
			if (currentDeliveryNotice.getTargetShop() != null) {
				country = currentDeliveryNotice.getTargetShop().getCountry();
			}
		}
		switch (country) {
		case AT:
			return atPriceStrategy;
		case DE:
			return dePriceStrategy;
		}

		// no target shop and no user, something strange is going on
		throw new RuntimeException("could not get price strategy for notepad");
	}

	@Override
	public void onPrintNotepadStickers(PrintNotepadStickersEvent event) {
		if (currentNotepad.getArticles().size() > 0) {
			notepadPrinter.printNotepad(currentNotepad);
		} else {
			Xfashion.fireError(errorMessages.noArticlesInNotepad());
		}
	}

	@Override
	public void onClearNotepad(ClearNotepadEvent event) {
		resetNotepad();
	}

	@Override
	public void onNotepadAddArticle(NotepadAddArticleEvent event) {
		final ArticleTypeDTO articleType = event.getArticleType();
		currentNotepad.addArticle(articleType, event.getAmount());
		articleProvider.setList(currentNotepad.getArticles());
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			public void execute() {
				Xfashion.eventBus.fireEvent(new RequestCheckNotepadPositionEvent());
			}
		});
		if (articleType.getUsed() != null && !articleType.getUsed()) {
			Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
				public void execute() {
					Xfashion.eventBus.fireEvent(new UpdateArticleTypesEvent(articleType, null));
				}
			});
		}
	}

	@Override
	public void onNotepadRemoveArticle(NotepadRemoveArticleEvent event) {
		currentNotepad.deductArticleType(event.getArticleType(), event.getAmount());
		articleProvider.setList(currentNotepad.getArticles());
	}

	@Override
	public void onRecordArticles(RecordArticlesEvent event) {
		scanArticlePopup.show();
	}

	@Override
	public void onPrintDeliveryNotice(PrintDeliveryNoticeEvent event) {
		if (currentNotepad.getArticles().size() == 0) {
			Xfashion.fireError(errorMessages.noArticlesInNotepad());
		} else if (currentDeliveryNotice == null) {
			Xfashion.fireError(errorMessages.noDeliveryNotice());
		} else {
			notepadPrinter.printDeliveryNotice(currentDeliveryNotice);
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
		Xfashion.eventBus.fireEvent(new DeliveryNoticeUpdatedEvent(event.getDeliveryNotice()));
	}

	@Override
	public void onRequestIntoStock(RequestIntoStockEvent event) {
		if (currentDeliveryNotice != null) {
			Xfashion.fireError(errorMessages.cannotAddDeliveryNoticeToStock());
			return;
		}
		if (currentNotepad == null || currentNotepad.getArticles().size() == 0) {
			Xfashion.fireError(errorMessages.notepadEmpty());
			return;
		}
		Xfashion.eventBus.fireEvent(new IntoStockEvent(currentNotepad));
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

	private void resetNotepad() {
		currentNotepad.getArticles().clear();
		currentNotepad.setCreationDate(new Date());
		currentDeliveryNotice = null;
		refreshProvider();
	}

	private void refreshProvider() {
		articleProvider.setList(currentNotepad.getArticles());
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
				Xfashion.eventBus.fireEvent(new DeliveryNoticeUpdatedEvent(result));
				currentDeliveryNotice = result;
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
		Xfashion.eventBus.addHandler(RequestIntoStockEvent.TYPE, this);
	}

}
