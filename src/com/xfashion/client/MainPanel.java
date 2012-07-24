package com.xfashion.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.xfashion.client.at.ArticleTypeManagement;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.db.ArticleTypeService;
import com.xfashion.client.db.ArticleTypeServiceAsync;
import com.xfashion.client.notepad.NotepadManagement;
import com.xfashion.client.pricechange.PriceChangeManagement;
import com.xfashion.client.promo.PromoManagement;
import com.xfashion.client.sell.SellStatisticManagement;
import com.xfashion.client.stock.StockManagement;
import com.xfashion.client.user.LoginEvent;
import com.xfashion.client.user.LoginHandler;
import com.xfashion.client.user.UserManagement;
import com.xfashion.client.user.UserProfile;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.UserRole;

public class MainPanel implements ErrorHandler, LoginHandler {

	private boolean DEV_MODE = false;
	
	private Panel contentPanel;

	private ArticleTypeDatabase articleTypeDatabase;

	private ArticleTypeManagement articleTypeManagement;

	private UserManagement userManagement;
	
	private NotepadManagement notepadManagement;
	
	private UserProfile userProfile;
	
	private StockManagement stockManagement;
	
	private SellStatisticManagement sellStatisticManagement; 

	private ErrorPopup errorPopup;

	private PromoManagement promoManagement;
	
	private PriceChangeManagement priceChangeManagement;
	
	public MainPanel() {
		articleTypeDatabase = new ArticleTypeDatabase();
		articleTypeDatabase.init();
		userManagement = new UserManagement();
		notepadManagement = new NotepadManagement(articleTypeDatabase);
		stockManagement = new StockManagement(articleTypeDatabase);
		sellStatisticManagement = new SellStatisticManagement(articleTypeDatabase);
		promoManagement = new PromoManagement();
		priceChangeManagement = new PriceChangeManagement(articleTypeDatabase);

		contentPanel = new SimplePanel();
		RootPanel.get("mainPanelContainer").add(contentPanel);
		articleTypeManagement = new ArticleTypeManagement(articleTypeDatabase); 
		userProfile = new UserProfile();
		
		registerEventHandlers();
	}

	public void showArticleTypePanel() {
		if (!DEV_MODE && UserManagement.user == null) {
			showUserProfilePanel();
		} else {
			contentPanel.clear();
			Panel panel = articleTypeManagement.getPanel(notepadManagement.getArticleProvider());
			contentPanel.add(panel);
		}
	}

	public void showUserManagementPanel() {
		if (!DEV_MODE && UserManagement.user == null) {
			showUserProfilePanel();
		} else {
			contentPanel.clear();
			Panel panel = userManagement.getPanel();
			contentPanel.add(panel);
		}
	}
	
	public void showUserProfilePanel() {
		contentPanel.clear();
		Panel panel = userProfile.getPanel();
		contentPanel.add(panel);
	}

	public void showStockPanel() {
		contentPanel.clear();
		Panel panel = stockManagement.getPanel(notepadManagement.getArticleProvider());
		contentPanel.add(panel);
	}

	public void showSellStatsticPanel() {
		contentPanel.clear();
		Panel panel = sellStatisticManagement.getPanel();
		contentPanel.add(panel);
	}

	public void showPromoPanel() {
		contentPanel.clear();
		Panel panel = promoManagement.getPanel();
		contentPanel.add(panel);
	}

	public void showPriceChangePanel() {
		contentPanel.clear();
		Panel panel = priceChangeManagement.getPanel();
		contentPanel.add(panel);
	}
	
	@Override
	public void onLogin(LoginEvent event) {
		if (UserManagement.hasRole(UserRole.SHOP)) {
			showStockPanel();
		} else {
			showArticleTypePanel();
		}
	}
	
	@Override
	public void onError(ErrorEvent event) {
		if (errorPopup == null) {
			errorPopup = new ErrorPopup();
		}
		errorPopup.showPopup(event.getErrorMessage());
	}

	public void test() {
		ArticleTypeServiceAsync articleTypeService = (ArticleTypeServiceAsync) GWT.create(ArticleTypeService.class);
		AsyncCallback<List<SizeDTO>> callback = new AsyncCallback<List<SizeDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(List<SizeDTO> result) {
				String str = "";
				for (SizeDTO s : result) {
					str = str + s.getName() + ", ";
				}
			}
		};
		articleTypeService.readSizes(callback);
	}

	private void registerEventHandlers() {
		Xfashion.eventBus.addHandler(ErrorEvent.TYPE, this);
		Xfashion.eventBus.addHandler(LoginEvent.TYPE, this);
	}

}
