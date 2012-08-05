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
import com.xfashion.client.at.ArticleFilterProvider;
import com.xfashion.client.at.event.ArticlesLoadedEvent;
import com.xfashion.client.at.event.ArticlesLoadedHandler;
import com.xfashion.client.at.event.RefreshFilterEvent;
import com.xfashion.client.at.event.RefreshFilterHandler;
import com.xfashion.client.at.event.RequestShowArticleTypeDetailsEvent;
import com.xfashion.client.at.event.RequestShowArticleTypeDetailsHandler;
import com.xfashion.client.at.popup.ArticleTypeDetailPopup;
import com.xfashion.client.at.popup.ArticleTypePopup;
import com.xfashion.client.at.sort.DefaultArticleAmountComparator;
import com.xfashion.client.at.sort.IArticleAmountComparator;
import com.xfashion.client.dialog.YesNoCallback;
import com.xfashion.client.dialog.YesNoPopup;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.notepad.event.ClearNotepadEvent;
import com.xfashion.client.notepad.event.IntoStockEvent;
import com.xfashion.client.notepad.event.IntoStockHandler;
import com.xfashion.client.promo.PromoService;
import com.xfashion.client.promo.PromoServiceAsync;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.stock.event.RemoveFromStockEvent;
import com.xfashion.client.stock.event.RemoveFromStockHandler;
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

public class StockManagement implements IntoStockHandler, SellFromStockHandler, RequestOpenSellPopupHandler, LoginHandler, ArticlesLoadedHandler,
		RequestShowArticleTypeDetailsHandler, RemoveFromStockHandler, RefreshFilterHandler {

	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	private PromoServiceAsync promoService = (PromoServiceAsync) GWT.create(PromoService.class);

	private HashMap<Long, PromoDTO> promos;

	protected StockFilterProvider stockFilterProvider;
	protected IArticleAmountComparator sortStrategy;

	private StockPanel stockPanel;
	private Panel panel;
	private SellFromStockPopup sellFromStockPopup;
	protected ArticleTypePopup articleTypePopup;

	TextMessages textMessages;

	public StockManagement(ArticleFilterProvider articleFilterProvider) {
		this.promos = new HashMap<Long, PromoDTO>();
		StockDataProvider stockProvider = new StockDataProvider(articleFilterProvider.getArticleTypeProvider());
		this.stockFilterProvider = new StockFilterProvider(articleFilterProvider.getArticleTypeProvider(), stockProvider);
		this.stockPanel = new StockPanel(stockFilterProvider);
		this.textMessages = GWT.create(TextMessages.class);

		registerForEvents();
	}

	public void init() {
		stockFilterProvider.init();
	}

	@Override
	public void onIntoStock(final IntoStockEvent event) {
		long cnt = 0L;
		for (ArticleAmountDTO articleAmount : event.getNotepad().getArticles()) {
			cnt += articleAmount.getAmount();
		}
		final long addedArticleNumber = cnt;
		YesNoPopup confirmIntoStock = new YesNoPopup(textMessages.confirmIntoStock(addedArticleNumber), new YesNoCallback() {
			@Override
			public void yes() {
				intoStock(event.getNotepad().getArticles(), addedArticleNumber);
			}

			@Override
			public void no() {
				// nothing to do
			}
		});
		confirmIntoStock.show();
	}

	private void intoStock(final Collection<ArticleAmountDTO> articles, final long addedArticleNumber) {
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
		userService.addStockEntries(articles, callback);
		Xfashion.eventBus.fireEvent(new ClearNotepadEvent());
	}

	@Override
	public void onRequestOpenSellPopup(RequestOpenSellPopupEvent event) {
		if (sellFromStockPopup == null) {
			sellFromStockPopup = new SellFromStockPopup(stockFilterProvider.getStockProvider());
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
	public void onRemoveFromStock(RemoveFromStockEvent event) {
		final ArticleAmountDTO articleToRemove = stockFilterProvider.getStockProvider().getStock().get(event.getArticleType().getKey());
		AsyncCallback<ArticleAmountDTO> callback = new AsyncCallback<ArticleAmountDTO>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(ArticleAmountDTO result) {
				storeRemoveResult(articleToRemove, result);
			}
		};
		userService.removeOneFromStock(articleToRemove, callback);
	}

	protected void storeRemoveResult(ArticleAmountDTO articleToRemove, ArticleAmountDTO result) {
		if (result == null) {
			stockFilterProvider.getStockProvider().getList().remove(articleToRemove);
			stockFilterProvider.getStockProvider().getStock().remove(articleToRemove.getArticleTypeKey());
		} else {
			articleToRemove.setAmount(result.getAmount());
		}
		stockFilterProvider.getStockProvider().refresh();
	}

	@Override
	public void onRequestShowArticleTypeDetails(RequestShowArticleTypeDetailsEvent event) {
		if (UserManagement.hasRole(UserRole.SHOP)) {
			if (articleTypePopup == null) {
				articleTypePopup = new ArticleTypeDetailPopup(stockFilterProvider, stockFilterProvider.getStockProvider());
			}
			articleTypePopup.showPopup(event.getArticleType());
		}
	}

	@Override
	public void onLogin(LoginEvent event) {
		readStock();
	}

	@Override
	public void onArticlesLoaded(ArticlesLoadedEvent event) {
		refresh();
	}

	@Override
	public void onRefreshFilter(RefreshFilterEvent event) {
		refresh();
	}

	public Panel getPanel(ArticleAmountDataProvider notepadArticleProvider) {
		if (panel == null) {
			sortStrategy = new DefaultArticleAmountComparator(stockFilterProvider);
			panel = stockPanel.createPanel(stockFilterProvider.getStockProvider(), notepadArticleProvider);
			refresh();
		}
		readPromos();
		return panel;
	}

	protected void storeStock(Collection<ArticleAmountDTO> result) {
		stockFilterProvider.getStockProvider().getStock().clear();
		List<ArticleAmountDTO> list = new ArrayList<ArticleAmountDTO>();
		for (ArticleAmountDTO aa : result) {
			stockFilterProvider.getStockProvider().getStock().put(aa.getArticleTypeKey(), aa);
			list.add(aa);
		}

		Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
		Xfashion.eventBus.fireEvent(new StockLoadedEvent(stockFilterProvider.getStockProvider()));
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
		stockFilterProvider.getArticleTypeProvider().applyFilters(stockFilterProvider);
		stockFilterProvider.updateProviders();

		List<ArticleTypeDTO> filteredArticleTypes = stockFilterProvider.getArticleTypeProvider().getList();
		List<ArticleAmountDTO> filteredStockItems = new ArrayList<ArticleAmountDTO>();
		for (ArticleTypeDTO articleType : filteredArticleTypes) {
			ArticleAmountDTO stockArticle = stockFilterProvider.getStockProvider().getStock().get(articleType.getKey());
			if (stockArticle != null) {
				filteredStockItems.add(stockArticle);
			}
		}
		if (sortStrategy != null) {
			Collections.sort(filteredStockItems, sortStrategy);
		}
		stockFilterProvider.getStockProvider().setList(filteredStockItems);
		stockFilterProvider.getStockProvider().refresh();
	}

	private void registerForEvents() {
		stockFilterProvider.getFilterEventBus().addHandler(RefreshFilterEvent.TYPE, this);
		Xfashion.eventBus.addHandler(IntoStockEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestOpenSellPopupEvent.TYPE, this);
		Xfashion.eventBus.addHandler(SellFromStockEvent.TYPE, this);
		Xfashion.eventBus.addHandler(LoginEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ArticlesLoadedEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestShowArticleTypeDetailsEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RemoveFromStockEvent.TYPE, this);
	}

}
