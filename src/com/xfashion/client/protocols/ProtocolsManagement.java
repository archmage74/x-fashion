package com.xfashion.client.protocols;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.protocols.event.AddMoreSoldArticlesEvent;
import com.xfashion.client.protocols.event.AddMoreSoldArticlesHandler;
import com.xfashion.client.protocols.event.ShowSellStatisticEvent;
import com.xfashion.client.protocols.event.ShowSellStatisticHandler;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.user.UserManagement;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.AddedArticleDTO;
import com.xfashion.shared.ShopDTO;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.UserRole;

public class ProtocolsManagement implements ShowSellStatisticHandler, AddMoreSoldArticlesHandler {
	
	public static final int CHUNK_SIZE = 50;
	
	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);

	private ProtocolsPanel sellStatisticPanel;
	private Panel panel;
	
	private ShopDTO currentShop;
	
	protected ArticleTypeDatabase articleTypeDatabase;
	private SoldArticleDataProvider sellStatisticProvider;
	private AddedArticleDataProvider addedArticleProvider;
	
	TextMessages textMessages;

	
	public ProtocolsManagement(ArticleTypeDatabase articleTypeDatabase) {
		this.textMessages = GWT.create(TextMessages.class);
		this.articleTypeDatabase = articleTypeDatabase;
		this.sellStatisticProvider = new SoldArticleDataProvider(articleTypeDatabase);
		this.addedArticleProvider = new AddedArticleDataProvider(articleTypeDatabase);

		this.sellStatisticPanel = new ProtocolsPanel(articleTypeDatabase);

		registerForEvents();
	}
	
	public Panel getPanel() {
		if (panel == null) {
			panel = sellStatisticPanel.createPanel(sellStatisticProvider, addedArticleProvider);
		}
		refresh();
		return panel;
	}
	
	@Override
	public void onSellFromStock(ShowSellStatisticEvent event) {
		currentShop = event.getShop();
		readSoldArticles();
		readAddedArticles();
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
	
	private void addMoreAddedArticles() {
		int from = addedArticleProvider.getList().size();
		int to = from + CHUNK_SIZE;
		AsyncCallback<List<AddedArticleDTO>> callback = new AsyncCallback<List<AddedArticleDTO>>() {
			@Override
			public void onSuccess(List<AddedArticleDTO> result) {
				addAddedArticles(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		if (currentShop == null) {
			userService.readWareInput(from, to, callback);
		} else {
			userService.readWareInputOfShop(currentShop.getKeyString(), from, to, callback);
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
		readAddedArticles();
	}
	
	private void readSoldArticles() {
		clearSoldArticles();
		addMoreSoldArticles();
	}
	
	private void readAddedArticles() {
		clearAddedArticles();
		addMoreAddedArticles();
	}
	
	private void clearSoldArticles() {
		sellStatisticProvider.getList().clear();
		sellStatisticPanel.enableAddMoreSoldArticles();
	}
	
	private void clearAddedArticles() {
		addedArticleProvider.getList().clear();
		sellStatisticPanel.enableAddMoreAddedArticles();
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
	
	private void addSoldArticles(List<SoldArticleDTO> result) {
		sellStatisticProvider.getList().addAll(result);
		if (result.size() < CHUNK_SIZE) {
			sellStatisticPanel.disableAddMoreSoldArticles();
		}
	}
	
	private void addAddedArticles(List<AddedArticleDTO> result) {
		addedArticleProvider.getList().addAll(result);
		if (result.size() < CHUNK_SIZE) {
			sellStatisticPanel.disableAddMoreAddedArticles();
		}
	}
	
	private void registerForEvents() {
		Xfashion.eventBus.addHandler(ShowSellStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(AddMoreSoldArticlesEvent.TYPE, this);
	}

}
