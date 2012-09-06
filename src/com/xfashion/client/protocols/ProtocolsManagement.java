package com.xfashion.client.protocols;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleFilterProvider;
import com.xfashion.client.protocols.event.AddMoreAddedArticlesEvent;
import com.xfashion.client.protocols.event.AddMoreAddedArticlesHandler;
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

public class ProtocolsManagement implements ShowSellStatisticHandler, AddMoreSoldArticlesHandler, AddMoreAddedArticlesHandler {
	
	public static final int CHUNK_SIZE = 50;
	
	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);

	private ProtocolsPanel protocolsPanel;
	private Panel panel;
	
	private ShopDTO currentShop;
	
	protected ArticleFilterProvider articleTypeDatabase;
	private SoldArticleDataProvider sellStatisticProvider;
	private AddedArticleDataProvider addedArticleProvider;
	
	TextMessages textMessages;
	
	public ProtocolsManagement(ArticleFilterProvider articleProvider) {
		this.textMessages = GWT.create(TextMessages.class);
		this.articleTypeDatabase = articleProvider;
		this.sellStatisticProvider = new SoldArticleDataProvider(articleProvider.getArticleTypeProvider());
		this.addedArticleProvider = new AddedArticleDataProvider(articleProvider.getArticleTypeProvider());

		this.protocolsPanel = new ProtocolsPanel(articleProvider);

		registerForEvents();
	}
	
	public Panel getPanel() {
		if (panel == null) {
			panel = protocolsPanel.createPanel(sellStatisticProvider, addedArticleProvider);
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
	
	@Override
	public void onAddMoreAddedArticles(AddMoreAddedArticlesEvent event) {
		addMoreAddedArticles();
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
		protocolsPanel.enableAddMoreSoldArticles();
	}
	
	private void clearAddedArticles() {
		addedArticleProvider.getList().clear();
		protocolsPanel.enableAddMoreAddedArticles();
	}
	
	private void readShops() {
		AsyncCallback<List<UserDTO>> callback = new AsyncCallback<List<UserDTO>>() {
			@Override
			public void onSuccess(List<UserDTO> result) {
				protocolsPanel.setUsers(result);
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
			protocolsPanel.disableAddMoreSoldArticles();
		}
	}
	
	private void addAddedArticles(List<AddedArticleDTO> result) {
		addedArticleProvider.getList().addAll(result);
		if (result.size() < CHUNK_SIZE) {
			protocolsPanel.disableAddMoreAddedArticles();
		}
	}
	
	private void registerForEvents() {
		Xfashion.eventBus.addHandler(ShowSellStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(AddMoreSoldArticlesEvent.TYPE, this);
		Xfashion.eventBus.addHandler(AddMoreAddedArticlesEvent.TYPE, this);
	}

}
