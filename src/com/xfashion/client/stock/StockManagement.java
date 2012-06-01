package com.xfashion.client.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.notepad.event.ClearNotepadEvent;
import com.xfashion.client.notepad.event.IntoStockEvent;
import com.xfashion.client.notepad.event.IntoStockHandler;
import com.xfashion.client.user.LoginEvent;
import com.xfashion.client.user.LoginHandler;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.notepad.ArticleAmountDTO;

public class StockManagement implements IntoStockHandler, LoginHandler {

	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);

	private HashMap<String, ArticleAmountDTO> stock;

	ArticleAmountDataProvider stockProvider;
	private StockPanel stockPanel;
	private Panel panel;
	
	public StockManagement(ArticleTypeDatabase articleTypeDatabase) {
		this.stockProvider = new ArticleAmountDataProvider(articleTypeDatabase);
		this.stockPanel = new StockPanel(articleTypeDatabase);
		registerForEvents();
	}
	
	@Override
	public void onIntoStock(IntoStockEvent event) {
		for (ArticleAmountDTO aa : event.getNotepad().getArticles()) {
			ArticleAmountDTO stockItem = stock.get(aa.getArticleTypeKey());
			if (stockItem == null) {
				createStockItem(aa);
			} else {
				stockItem.increaseAmount(aa.getAmount());
				updateStockItem(stockItem);
			}
		}
		Xfashion.eventBus.fireEvent(new ClearNotepadEvent());
	}

	private void createStockItem(ArticleAmountDTO articleAmount) {
		AsyncCallback<ArticleAmountDTO> callback = new AsyncCallback<ArticleAmountDTO>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(ArticleAmountDTO result) {
				stock.put(result.getArticleTypeKey(), result);
				stockProvider.getList().add(result);
				stockProvider.flush();
				stockProvider.refresh();
			}
		};
		userService.createStockEntry(articleAmount, callback);
	}

	private void updateStockItem(ArticleAmountDTO articleAmount) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				stockProvider.flush();
				stockProvider.refresh();
			}
		};
		userService.updateStockEntry(articleAmount, callback);
	}
	
	@Override
	public void onLogin(LoginEvent event) {
		readStock();
	}
	
	public Panel getPanel() {
		if (panel == null) {
			panel = stockPanel.createPanel(stockProvider);
		}
		return panel;
	}

	private void readStock() {
		AsyncCallback<Set<ArticleAmountDTO>> callback = new AsyncCallback<Set<ArticleAmountDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(Set<ArticleAmountDTO> result) {
				storeStock(result);
			}
		};
		userService.readStock(callback);
	}

	protected void storeStock(Set<ArticleAmountDTO> result) {
		stock = new HashMap<String, ArticleAmountDTO>();
		List<ArticleAmountDTO> list = new ArrayList<ArticleAmountDTO>();
		for (ArticleAmountDTO aa : result) {
			stock.put(aa.getArticleTypeKey(), aa);
			list.add(aa);
		}
		stockProvider.setList(list);
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(IntoStockEvent.TYPE, this);
		Xfashion.eventBus.addHandler(LoginEvent.TYPE, this);
	}

}
