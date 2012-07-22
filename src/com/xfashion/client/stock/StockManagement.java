package com.xfashion.client.stock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.db.event.FilterRefreshedEvent;
import com.xfashion.client.db.event.FilterRefreshedHandler;
import com.xfashion.client.db.event.RefreshFilterEvent;
import com.xfashion.client.db.sort.DefaultArticleAmountComparator;
import com.xfashion.client.db.sort.IArticleAmountComparator;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.notepad.event.ClearNotepadEvent;
import com.xfashion.client.notepad.event.IntoStockEvent;
import com.xfashion.client.notepad.event.IntoStockHandler;
import com.xfashion.client.promo.PromoService;
import com.xfashion.client.promo.PromoServiceAsync;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.stock.event.RequestOpenSellPopupEvent;
import com.xfashion.client.stock.event.RequestOpenSellPopupHandler;
import com.xfashion.client.stock.event.SellFromStockEvent;
import com.xfashion.client.stock.event.SellFromStockHandler;
import com.xfashion.client.stock.event.StockLoadedEvent;
import com.xfashion.client.user.LoginEvent;
import com.xfashion.client.user.LoginHandler;
import com.xfashion.client.user.UserManagement;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.PromoDTO;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.UserRole;

public class StockManagement implements IntoStockHandler, SellFromStockHandler, RequestOpenSellPopupHandler, LoginHandler, FilterRefreshedHandler {

	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	private PromoServiceAsync promoService = (PromoServiceAsync) GWT.create(PromoService.class);
	
	private HashMap<Long, PromoDTO> promos;

	protected StockDataProvider stockProvider;
	protected ArticleTypeDatabase articleTypeDatabase;
	protected IArticleAmountComparator sortStrategy;

	private StockPanel stockPanel;
	private Panel panel;
	private SellFromStockPopup sellFromStockPopup;

	TextMessages textMessages;

	public StockManagement(ArticleTypeDatabase articleTypeDatabase) {
		this.promos = new HashMap<Long, PromoDTO>();
		this.articleTypeDatabase = articleTypeDatabase;
		this.stockProvider = new StockDataProvider(articleTypeDatabase);
		this.stockPanel = new StockPanel(articleTypeDatabase);

		this.textMessages = GWT.create(TextMessages.class);
		
		registerForEvents();
	}

	@Override
	public void onIntoStock(IntoStockEvent event) {
		Long cnt = 0L;
		for (ArticleAmountDTO articleAmount : event.getNotepad().getArticles()) {
			cnt += articleAmount.getAmount();
		}
		final Long addedArticleNumber = cnt;
		AsyncCallback<Collection<ArticleAmountDTO>> callback = new AsyncCallback<Collection<ArticleAmountDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(Collection<ArticleAmountDTO> result) {
				storeStock(result);
				Xfashion.fireError(textMessages.intoStockResult(addedArticleNumber));
			}
		};
		userService.addStockEntries(event.getNotepad().getArticles(), callback);
		Xfashion.eventBus.fireEvent(new ClearNotepadEvent());
	}

	@Override
	public void onRequestOpenSellPopup(RequestOpenSellPopupEvent event) {
		if (sellFromStockPopup == null) {
			sellFromStockPopup = new SellFromStockPopup(stockProvider);
		}
		sellFromStockPopup.show(promos);
	}

	@Override
	public void onSellFromStock(SellFromStockEvent event) {
		Long cnt = 0L;
		for (SoldArticleDTO articleAmount : event.getArticles()) {
			cnt += articleAmount.getAmount();
		}
		final Long soldArticleNumber = cnt;
		AsyncCallback<Collection<ArticleAmountDTO>> callback = new AsyncCallback<Collection<ArticleAmountDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(Collection<ArticleAmountDTO> result) {
				storeStock(result);
				Xfashion.fireError(textMessages.sellStockResult(soldArticleNumber));
			}
		};
		userService.sellArticlesFromStock(event.getArticles(), callback);
	}

	@Override
	public void onLogin(LoginEvent event) {
		if (UserManagement.hasRole(UserRole.SHOP)) {
			readStock();
		}
	}

	@Override
	public void onFilterRefreshed(FilterRefreshedEvent event) {
		refresh();
	}

	public Panel getPanel(ArticleAmountDataProvider notepadArticleProvider) {
		if (panel == null) {
			panel = stockPanel.createPanel(stockProvider, notepadArticleProvider);
			sortStrategy = new DefaultArticleAmountComparator(articleTypeDatabase);
		}
		readPromos();
		return panel;
	}

	protected void storeStock(Collection<ArticleAmountDTO> result) {
		stockProvider.getStock().clear();
		List<ArticleAmountDTO> list = new ArrayList<ArticleAmountDTO>();
		for (ArticleAmountDTO aa : result) {
			stockProvider.getStock().put(aa.getArticleTypeKey(), aa);
			list.add(aa);
		}
		articleTypeDatabase.setArticleAmounts(stockProvider.getStock());
		Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
		Xfashion.eventBus.fireEvent(new StockLoadedEvent(stockProvider));
		refresh();
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
		userService.readOwnStock(callback);
	}

	private void readPromos() {
		AsyncCallback<List<PromoDTO>> callback = new AsyncCallback<List<PromoDTO>>() {
			@Override
			public void onSuccess(List<PromoDTO> result) {
				storePromos(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		promoService.readActivePromos(callback);
	}

	private void storePromos(List<PromoDTO> promoList) {
		promos.clear();
		for (PromoDTO promo : promoList) {
			promos.put(promo.getEan(), promo);
		}
	}
	
	private void refresh() {
		List<ArticleTypeDTO> filteredArticleTypes = articleTypeDatabase.getArticleTypeProvider().getList();
		List<ArticleAmountDTO> filteredStockItems = new ArrayList<ArticleAmountDTO>();
		for (ArticleTypeDTO articleType : filteredArticleTypes) {
			ArticleAmountDTO stockArticle = stockProvider.getStock().get(articleType.getKey());
			if (stockArticle != null) {
				filteredStockItems.add(stockArticle);
			}
		}
		if (sortStrategy != null) {
			Collections.sort(filteredStockItems, sortStrategy);
		}
		stockProvider.setList(filteredStockItems);
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(IntoStockEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestOpenSellPopupEvent.TYPE, this);
		Xfashion.eventBus.addHandler(SellFromStockEvent.TYPE, this);
		Xfashion.eventBus.addHandler(LoginEvent.TYPE, this);
		Xfashion.eventBus.addHandler(FilterRefreshedEvent.TYPE, this);
	}

}
