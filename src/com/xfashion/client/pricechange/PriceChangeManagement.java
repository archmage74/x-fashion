package com.xfashion.client.pricechange;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.db.sort.DefaultArticleAmountComparator;
import com.xfashion.client.db.sort.IArticleAmountComparator;
import com.xfashion.client.notepad.NotepadPrinter;
import com.xfashion.client.pricechange.event.AcceptPriceChangesEvent;
import com.xfashion.client.pricechange.event.AcceptPriceChangesHandler;
import com.xfashion.client.pricechange.event.PriceChangesUpdatedEvent;
import com.xfashion.client.pricechange.event.PrintChangePriceStickersEvent;
import com.xfashion.client.pricechange.event.PrintChangePriceStickersHandler;
import com.xfashion.client.stock.StockDataProvider;
import com.xfashion.client.stock.event.StockLoadedEvent;
import com.xfashion.client.stock.event.StockLoadedHandler;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.NotepadDTO;
import com.xfashion.shared.PriceChangeDTO;

public class PriceChangeManagement implements StockLoadedHandler, PrintChangePriceStickersHandler, AcceptPriceChangesHandler {

	protected UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	
	protected ArticleTypeDatabase articleTypeDatabase;
	protected PriceChangeArticleAmountDataProvider changeArticleTypesProvider;
	protected StockDataProvider stockProvider;
	
	protected IArticleAmountComparator sortStrategy;
	protected NotepadPrinter notepadPrinter;

	protected PriceChangePanel priceChangePanel;
	protected Panel panel;
	
	public PriceChangeManagement(ArticleTypeDatabase articleTypeDatabase) {
		this.articleTypeDatabase = articleTypeDatabase;
		this.changeArticleTypesProvider = new PriceChangeArticleAmountDataProvider(articleTypeDatabase);
		this.priceChangePanel = new PriceChangePanel(articleTypeDatabase);
		this.notepadPrinter = new NotepadPrinter();
		registerForEvents();
	}

	public Panel getPanel() {
		if (panel == null) {
			panel = priceChangePanel.createPanel(changeArticleTypesProvider);
			sortStrategy = new DefaultArticleAmountComparator(articleTypeDatabase);
		}
		refreshProvider();
		return panel;
	}

	@Override
	public void onStockLoaded(StockLoadedEvent event) {
		stockProvider = event.getStockProvider();
		refreshProvider();
	}

	@Override
	public void onPrintChangePriceStickers(PrintChangePriceStickersEvent event) {
		printStickers(event.getArticleAmount());
		acceptPriceChange(event.getArticleAmount());
	}

	@Override
	public void onAcceptPriceChanges(AcceptPriceChangesEvent event) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError("Error while accepting price-changes: " + caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				refreshProvider();
			}
		};
		userService.deletePriceChanges(changeArticleTypesProvider.getPriceChanges(), callback);
	}
	
	private void refreshProvider() {
		changeArticleTypesProvider.getList().clear();
		readPriceChanges();
	}
	
	private void readPriceChanges() {
		AsyncCallback<Collection<PriceChangeDTO>> callback = new AsyncCallback<Collection<PriceChangeDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError("Error while reading price-changes: " + caught.getMessage());
			}
			@Override
			public void onSuccess(Collection<PriceChangeDTO> result) {
				providePriceChanges(result);
			}
		};
		userService.readOwnPriceChanges(callback);
	}

	protected void providePriceChanges(Collection<PriceChangeDTO> priceChanges) {
		this.changeArticleTypesProvider.setPriceChanges(priceChanges);
		HashSet<String> alreadyAdded = new HashSet<String>();
		for (PriceChangeDTO priceChange : priceChanges) {
			ArticleAmountDTO stockItem = stockProvider.getStock().get(priceChange.getArticleTypeKey());
			if (stockItem != null && !alreadyAdded.contains(stockItem.getArticleTypeKey())) {
				changeArticleTypesProvider.getList().add(stockItem);
				alreadyAdded.add(stockItem.getArticleTypeKey());
			}
		}
		Xfashion.eventBus.fireEvent(new PriceChangesUpdatedEvent(changeArticleTypesProvider.getList().size()));
	}

	private void acceptPriceChange(ArticleAmountDTO articleAmount) {
		Collection<PriceChangeDTO> updatedPriceChanges = new ArrayList<PriceChangeDTO>();
		for (PriceChangeDTO priceChange : changeArticleTypesProvider.getPriceChanges()) {
			if (priceChange.getArticleTypeKey().equals(articleAmount.getArticleTypeKey())) {
				priceChange.setAccepted(true);
				updatedPriceChanges.add(priceChange);
			}
		}
		updatePriceChanges(updatedPriceChanges);
	}

	private void updatePriceChanges(Collection<PriceChangeDTO> updatedPriceChanges) {
		AsyncCallback<Collection<PriceChangeDTO>> callback = new AsyncCallback<Collection<PriceChangeDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError("Error while accepting price-changes: " + caught.getMessage());
			}
			@Override
			public void onSuccess(Collection<PriceChangeDTO> result) {
				changeArticleTypesProvider.refresh();
			}
		};
		userService.updatePriceChanges(updatedPriceChanges, callback);
	}

	private void printStickers(ArticleAmountDTO articleAmount) {
		NotepadDTO notepad = new NotepadDTO();
		ArticleTypeDTO articleType = changeArticleTypesProvider.retrieveArticleType(articleAmount);
		notepad.addArticle(articleType, articleAmount.getAmount());
		notepadPrinter.printNotepad(notepad);
	}
	
	private void registerForEvents() {
		Xfashion.eventBus.addHandler(StockLoadedEvent.TYPE, this);
		Xfashion.eventBus.addHandler(PrintChangePriceStickersEvent.TYPE, this);
		Xfashion.eventBus.addHandler(AcceptPriceChangesEvent.TYPE, this);
	}
	
}
