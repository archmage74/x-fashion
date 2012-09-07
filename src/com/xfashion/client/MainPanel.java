package com.xfashion.client;

import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.admin.AdminPanel;
import com.xfashion.client.at.ArticleFilterProvider;
import com.xfashion.client.at.ArticleTypeManagement;
import com.xfashion.client.event.ContentPanelResizeEvent;
import com.xfashion.client.notepad.NotepadManagement;
import com.xfashion.client.pricechange.PriceChangeManagement;
import com.xfashion.client.promo.PromoManagement;
import com.xfashion.client.protocols.ProtocolsManagement;
import com.xfashion.client.removed.RemovedArticleManagement;
import com.xfashion.client.stat.StatisticManagement;
import com.xfashion.client.stock.StockManagement;
import com.xfashion.client.user.LoginEvent;
import com.xfashion.client.user.LoginHandler;
import com.xfashion.client.user.UserManagement;
import com.xfashion.client.user.UserProfile;
import com.xfashion.shared.UserRole;

public class MainPanel implements ErrorHandler, LoginHandler {

	public static int contentPanelHeight;

	private boolean DEV_MODE = false;
	
	private Panel contentPanel;

	private ArticleTypeManagement articleTypeManagement;

	private UserManagement userManagement;
	
	private NotepadManagement notepadManagement;
	
	private UserProfile userProfile;
	
	private StockManagement stockManagement;
	
	private ProtocolsManagement protocolsManagement;
	
	private StatisticManagement statisticManagement;
	
	private RemovedArticleManagement removedArticleManagement;

	private ErrorPopup errorPopup;

	private PromoManagement promoManagement;
	
	private PriceChangeManagement priceChangeManagement;
	
	public MainPanel() {
		articleTypeManagement = new ArticleTypeManagement();
		articleTypeManagement.init();
		ArticleFilterProvider articleFilterProvider = articleTypeManagement.getArticleFilterProvider();
		userManagement = new UserManagement();
		notepadManagement = NotepadManagement.getInstance();
		notepadManagement.init(articleFilterProvider);
		stockManagement = new StockManagement(articleFilterProvider, articleTypeManagement);
		stockManagement.init();
		protocolsManagement = new ProtocolsManagement(articleFilterProvider);
		statisticManagement = new StatisticManagement(articleFilterProvider);
		removedArticleManagement = new RemovedArticleManagement(articleFilterProvider);
		promoManagement = new PromoManagement();
		priceChangeManagement = new PriceChangeManagement(articleFilterProvider);

		RootPanel.get("logoContainer").add(createLogo());
		contentPanel = new SimplePanel();
		contentPanel.setSize("100%", "100%");
		RootPanel.get("mainPanelContainer").add(contentPanel);
		userProfile = new UserProfile();
		windowResize(Window.getClientWidth(), Window.getClientHeight());
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				windowResize(event.getWidth(), event.getHeight());
			}
		});
		registerEventHandlers();
	}

	protected void windowResize(int width, int height) {
		MainPanel.contentPanelHeight = height - 75;
		contentPanel.setHeight(contentPanelHeight + "px");
		Xfashion.eventBus.fireEvent(new ContentPanelResizeEvent(contentPanelHeight));
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
		Panel panel = protocolsManagement.getPanel();
		contentPanel.add(panel);
	}

	public void showStatsticPanel() {
		contentPanel.clear();
		Panel panel = statisticManagement.getPanel();
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
	
	public void showRemovedArticlesPanel() {
		contentPanel.clear();
		Panel panel = removedArticleManagement.getPanel();
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
		contentPanel.clear();
		AdminPanel adminPanel = new AdminPanel();
		contentPanel.add(adminPanel.createPanel());
	}

	private Widget createLogo() {
		Image img = new Image("XFashion-LOGO.png");
		img.setWidth("150px");
		img.setHeight("70px");
		img.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				int x = event.getClientX();
				int y = event.getClientY();
				if (UserManagement.hasRole(UserRole.SHOP) && y >= 29 && y <= 32 && x >= 143 && x <= 146) {
					showRemovedArticlesPanel();
				}
			}
		});
		return img;
	}

	private void registerEventHandlers() {
		Xfashion.eventBus.addHandler(ErrorEvent.TYPE, this);
		Xfashion.eventBus.addHandler(LoginEvent.TYPE, this);
	}

}
