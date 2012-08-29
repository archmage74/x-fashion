package com.xfashion.client.stock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleFilterProvider;
import com.xfashion.client.at.ArticleTypeManagement;
import com.xfashion.client.at.event.ArticlesLoadedEvent;
import com.xfashion.client.at.event.ArticlesLoadedHandler;
import com.xfashion.client.at.event.RefreshFilterEvent;
import com.xfashion.client.at.event.RefreshFilterHandler;
import com.xfashion.client.at.event.RequestShowArticleTypeDetailsByEanEvent;
import com.xfashion.client.at.event.RequestShowArticleTypeDetailsByEanHandler;
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
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.stock.event.LoadStockEvent;
import com.xfashion.client.stock.event.LoadStockHandler;
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
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.UserRole;

public class StockManagement implements IntoStockHandler, SellFromStockHandler, RequestOpenSellPopupHandler, LoginHandler, ArticlesLoadedHandler,
		RequestShowArticleTypeDetailsHandler, RequestShowArticleTypeDetailsByEanHandler, RemoveFromStockHandler, RefreshFilterHandler,
		LoadStockHandler {

	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	private PromoServiceAsync promoService = (PromoServiceAsync) GWT.create(PromoService.class);

	protected EventBus stockBus;

	private HashMap<Long, PromoDTO> promos;

	protected StockFilterProvider stockFilterProvider;
	protected IArticleAmountComparator sortStrategy;
	protected UserDTO currentUser;

	protected ArticleTypeManagement articleTypeManagement;

	private StockPanel stockPanel;
	private Panel panel;
	private SellFromStockPopup sellFromStockPopup;
	protected ArticleTypePopup articleTypePopup;

	protected TextMessages textMessages;
	protected ErrorMessages errorMessages;

	public StockManagement(ArticleFilterProvider articleFilterProvider, ArticleTypeManagement atm) {
		this.articleTypeManagement = atm;
		this.promos = new HashMap<Long, PromoDTO>();
		this.stockBus = new SimpleEventBus();

		StockDataProvider stockProvider = new StockDataProvider(articleFilterProvider.getArticleTypeProvider());
		this.stockFilterProvider = new StockFilterProvider(articleFilterProvider.getArticleTypeProvider(), stockProvider);

		this.stockPanel = new StockPanel(stockFilterProvider, stockBus);

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
		confirmIntoStock(currentUser, event.getNotepad().getArticles(), addedArticleNumber);
	}
	
	private void confirmIntoStock(final UserDTO user, final Collection<ArticleAmountDTO> articles, final long addedArticleNumber) {
		String confirmMessage = null;
		if (currentUser == null) {
			confirmMessage = textMessages.confirmIntoStock(addedArticleNumber);
		} else {
			confirmMessage = textMessages.confirmForeignIntoStock(addedArticleNumber, currentUser.getShop().getShortName());
		}

		YesNoPopup confirmIntoStock = new YesNoPopup(confirmMessage, new YesNoCallback() {
			@Override
			public void yes() {
				intoStock(user, articles, addedArticleNumber);
			}
			@Override
			public void no() {
				// nothing to do
			}
		});
		confirmIntoStock.show();
	}

	private void intoStock(final UserDTO user, final Collection<ArticleAmountDTO> articles, final long addedArticleNumber) {
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
		if (user == null) {
			userService.addStockEntries(articles, callback);
		} else {
			userService.addStockEntriesToUser(user.getKey(), articles, callback);
		}
		Xfashion.eventBus.fireEvent(new ClearNotepadEvent());
	}

	@Override
	public void onRequestOpenSellPopup(RequestOpenSellPopupEvent event) {
		if (sellFromStockPopup == null) {
			sellFromStockPopup = new SellFromStockPopup(stockFilterProvider.getStockProvider(), stockFilterProvider);
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
		final int amount = event.getAmount();
		if (amount > articleToRemove.getAmount()) {
			Xfashion.fireError(errorMessages.cannotRemoveMoreThanPresent(amount, articleToRemove.getAmount()));
			return;
		}
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
		userService.removeFromStock(articleToRemove, amount, callback);
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
	public void onRequestShowArticleTypeDetailsByEan(RequestShowArticleTypeDetailsByEanEvent event) {
		if (UserManagement.hasRole(UserRole.SHOP)) {
			if (articleTypePopup == null) {
				articleTypePopup = new ArticleTypeDetailPopup(stockFilterProvider, stockFilterProvider.getStockProvider());
			}
			ArticleTypeDTO articleType = stockFilterProvider.getArticleTypeProvider().retrieveArticleType(event.getEan());
			articleTypePopup.showPopup(articleType);
		}
	}

	@Override
	public void onLoadStock(LoadStockEvent event) {
		currentUser = event.getUser();
		readStock(event.getUser().getKey());
	}

	@Override
	public void onLogin(LoginEvent event) {
		readStock(null);
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
			panel = stockPanel.createPanel(articleTypeManagement, stockFilterProvider.getStockProvider(), notepadArticleProvider);
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
		stockFilterProvider.getStockProvider().setIsLoading(false);

		Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
		Xfashion.eventBus.fireEvent(new StockLoadedEvent(stockFilterProvider.getStockProvider()));
		refresh();
	}

	private void readStock(String shopKey) {
		stockFilterProvider.getStockProvider().setIsLoading(true);
		AsyncCallback<List<ArticleAmountDTO>> callback = new AsyncCallback<List<ArticleAmountDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(List<ArticleAmountDTO> result) {
				storeStock(result);
			}
		};
		if (shopKey == null) {
			userService.readOwnStock(callback);
		} else {
			userService.readStockOfUser(shopKey, callback);
		}
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
		if (panel == null) {
			return;
		}
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
		stockBus.addHandler(RefreshFilterEvent.TYPE, this);
		stockBus.addHandler(LoadStockEvent.TYPE, this);
		Xfashion.eventBus.addHandler(IntoStockEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestOpenSellPopupEvent.TYPE, this);
		Xfashion.eventBus.addHandler(SellFromStockEvent.TYPE, this);
		Xfashion.eventBus.addHandler(LoginEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ArticlesLoadedEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestShowArticleTypeDetailsEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestShowArticleTypeDetailsByEanEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RemoveFromStockEvent.TYPE, this);
	}

}
