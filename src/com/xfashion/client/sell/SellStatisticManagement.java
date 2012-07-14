package com.xfashion.client.sell;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.sell.event.AddMoreSoldArticlesEvent;
import com.xfashion.client.sell.event.AddMoreSoldArticlesHandler;
import com.xfashion.client.sell.event.ShowSellStatisticEvent;
import com.xfashion.client.sell.event.ShowSellStatisticHandler;
import com.xfashion.client.user.UserManagement;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.ShopDTO;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.UserRole;

public class SellStatisticManagement implements ShowSellStatisticHandler, AddMoreSoldArticlesHandler {
	
	public static final int CHUNK_SIZE = 50;
	
	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);

	private SellStatisticPanel sellStatisticPanel;
	private Panel panel;
	
	private ShopDTO currentShop;
	
	protected ArticleTypeDatabase articleTypeDatabase;
	private SoldArticleDataProvider sellStatisticProvider;
	
	TextMessages textMessages;
	
	public SellStatisticManagement(ArticleTypeDatabase articleTypeDatabase) {
		this.textMessages = GWT.create(TextMessages.class);
		this.articleTypeDatabase = articleTypeDatabase;
		this.sellStatisticProvider = new SoldArticleDataProvider(articleTypeDatabase);

		this.sellStatisticPanel = new SellStatisticPanel(articleTypeDatabase);

		registerForEvents();
	}
	
	public Panel getPanel() {
		if (panel == null) {
			panel = sellStatisticPanel.createPanel(sellStatisticProvider);
		}
		refresh();
		return panel;
	}
	
	@Override
	public void onSellFromStock(ShowSellStatisticEvent event) {
		currentShop = event.getShop();
		readSoldArticles();
	}
	
	@Override
	public void onAddMoreSoldArticles(AddMoreSoldArticlesEvent event) {
		addMoreSoldArticles();
	}
	
	private void addMoreSoldArticles() {
		int from = sellStatisticProvider.getList().size();
		int to = from + CHUNK_SIZE;
		AsyncCallback<List<SoldArticleDTO>> callback = new AsyncCallback<List<SoldArticleDTO>>() {
			@Override
			public void onSuccess(List<SoldArticleDTO> result) {
				addSoldArticles(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		if (currentShop == null) {
			userService.readSoldArticles(from, to, callback);
		} else {
			userService.readSoldArticlesOfShop(currentShop.getKeyString(), from, to, callback);
		}
	}
	
	private void refresh() {
		if (UserManagement.hasRole(UserRole.SHOP)) {
			currentShop = UserManagement.user.getShop();
		}
		if (UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
			readShops();
		}
		readSoldArticles();
	}
	
	private void readSoldArticles() {
		clearSoldArticles();
		addMoreSoldArticles();
	}
	
	private void clearSoldArticles() {
		sellStatisticProvider.getList().clear();
		sellStatisticPanel.enableAddMore();
	}
	
	private void readShops() {
		AsyncCallback<List<UserDTO>> callback = new AsyncCallback<List<UserDTO>>() {
			@Override
			public void onSuccess(List<UserDTO> result) {
				sellStatisticPanel.setUsers(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		userService.readUsers(callback);
	}
	
	protected void addSoldArticles(List<SoldArticleDTO> result) {
		sellStatisticProvider.getList().addAll(result);
		if (result.size() < CHUNK_SIZE) {
			sellStatisticPanel.disableAddMore();
		}
	}
	
	private void registerForEvents() {
		Xfashion.eventBus.addHandler(ShowSellStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(AddMoreSoldArticlesEvent.TYPE, this);
	}

}
