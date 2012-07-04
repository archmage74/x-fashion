package com.xfashion.client.sell;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.sell.event.AddMoreSoldArticlesEvent;
import com.xfashion.client.sell.event.AddMoreSoldArticlesHandler;
import com.xfashion.client.sell.event.ShowSellStatisticEvent;
import com.xfashion.client.sell.event.ShowSellStatisticHandler;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.ShopDTO;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.UserDTO;

public class SellStatisticManagement implements ShowSellStatisticHandler, AddMoreSoldArticlesHandler {

	public static final int CHUNK_SIZE = 50;
	
	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);

	private SellStatisticPanel sellStatisticPanel;
	private Panel panel;
	
	private List<SoldArticleDTO> soldArticles;
		
	TextMessages textMessages;

	public SellStatisticManagement() {
		textMessages = GWT.create(TextMessages.class);
		this.sellStatisticPanel = new SellStatisticPanel();
		soldArticles = new ArrayList<SoldArticleDTO>();
		registerForEvents();
	}

//	@Override
//	public void onSellFromStock(SellFromStockEvent event) {
//		Long cnt = 0L;
//		for (SoldArticleDTO articleAmount : event.getArticles()) {
//			cnt += articleAmount.getAmount();
//		}
//		final Long soldArticleNumber = cnt;
//		AsyncCallback<Collection<ArticleAmountDTO>> callback = new AsyncCallback<Collection<ArticleAmountDTO>>() {
//			@Override
//			public void onFailure(Throwable caught) {
//				Xfashion.fireError(caught.getMessage());
//			}
//			@Override
//			public void onSuccess(Collection<ArticleAmountDTO> result) {
//				storeStock(result);
//				Xfashion.fireError(textMessages.sellStockResult(soldArticleNumber));
//			}
//		};
//		userService.sellArticlesFromStock(event.getArticles(), callback);
//	}

	public Panel getPanel() {
		if (panel == null) {
			panel = sellStatisticPanel.createPanel();
		}
		refresh();
		return panel;
	}

	@Override
	public void onSellFromStock(ShowSellStatisticEvent event) {
		sellStatisticPanel.clearSoldArticles();
		if (event.getShop() == null) {
			readSoldArticlesOfAllShops();
		} else {
			readSoldArticles(event.getShop());
		}
	}

	@Override
	public void onAddMoreSoldArticles(AddMoreSoldArticlesEvent event) {
		addMoreSoldArticles();
	}

	private void addMoreSoldArticles() {
		int from = sellStatisticPanel.getNumberOfShownSoldArticles();
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
		userService.readSoldArticles(from, to, callback);
	}
	
	private void refresh() {
		readSoldArticlesOfAllShops();
		readShops();
	}
	
	private void readSoldArticlesOfAllShops() {
		clearSoldArticles();
		addMoreSoldArticles();
	}
	
	private void readSoldArticles(ShopDTO shopDTO) {
		clearSoldArticles();
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
		userService.readSoldArticlesOfShop(shopDTO.getKeyString(), 0, 100, callback);
	}

	private void clearSoldArticles() {
		soldArticles.clear();
		sellStatisticPanel.clearSoldArticles();
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
		soldArticles.addAll(result);
		sellStatisticPanel.addSoldArticles(result);
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(ShowSellStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(AddMoreSoldArticlesEvent.TYPE, this);
	}

}
