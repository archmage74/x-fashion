package com.xfashion.client.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.MainPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.pricechange.event.PriceChangesUpdatedEvent;
import com.xfashion.client.pricechange.event.PriceChangesUpdatedHandler;
import com.xfashion.client.resources.MenuMessages;
import com.xfashion.client.stock.event.RequestOpenSellPopupEvent;
import com.xfashion.client.user.LoginEvent;
import com.xfashion.client.user.LoginHandler;
import com.xfashion.client.user.UserManagement;
import com.xfashion.shared.UserRole;

public class MenuPanel implements LoginHandler, PriceChangesUpdatedHandler {

	private MenuMessages menuMessages;
	
	private MainPanel mainPanel;
	
	private VerticalPanel navPanel;
	
	private Label loggedInLabel;
	
	private MenuItem priceChangeItem;
	
	public MenuPanel(MainPanel mainPanel) {
		this.menuMessages = GWT.create(MenuMessages.class);
		this.mainPanel = mainPanel;
		registerForEvents();
	}
	
	@Override
	public void onLogin(LoginEvent event) {
		redraw();
		if (loggedInLabel != null) {
			loggedInLabel.setText(menuMessages.loggedInAs() + ": " + event.getUser().getUsername());
		}
	}
	
	@Override
	public void onPriceChangesUpdated(PriceChangesUpdatedEvent event) {
		priceChangeItem.setHTML(menuMessages.priceChanges(event.getPriceChangesAmount()));
	}
	
	public void addMenuPanel() {
		navPanel = new VerticalPanel();
		navPanel.addStyleName("navigationPanel");
		RootPanel.get("navPanelContainer").add(navPanel);
		
		Panel loggedInPanel = new HorizontalPanel();
		loggedInLabel = new Label();
		loggedInPanel.add(loggedInLabel);
		navPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		navPanel.add(loggedInPanel);
		
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		menu.setWidth("1500px");
		menu.setHeight("30px");
		menu.setAnimationEnabled(true);

		if (UserManagement.hasRole(UserRole.DEVELOPER, UserRole.ADMIN)) {
			addAdminMenuItems(menu);
		} else if (UserManagement.hasRole(UserRole.SHOP)) {
			addShopMenuItems(menu);
		}

		navPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		navPanel.add(menu);
	}
	
	private void addAdminMenuItems(MenuBar menu) {
		menu.addItem(createArticleTypeMenuItem());
		menu.addItem(createUserProfileMenuItem());
		menu.addItem(createSellStatisticMenuItem());
		menu.addItem(createPromoMenuItem());
		menu.addItem(createUserManagementMenuItem());
		if (UserManagement.hasRole(UserRole.DEVELOPER)) {
			menu.addItem(createTestMenuItem());
		}
	}
	
	private void addShopMenuItems(MenuBar menu) {
		menu.addItem(createStockMenuItem());
		menu.addItem(createUserProfileMenuItem());
		menu.addItem(createSellArticleMenuItem());
		menu.addItem(createSellStatisticMenuItem());
		menu.addItem(createPromoMenuItem());
		menu.addItem(createPriceChangeMenuItem());
	}
	
	public void redraw() {
		navPanel.clear();
		RootPanel.get("navPanelContainer").remove(navPanel);
		navPanel = null;
		addMenuPanel();
	}

	private MenuItem createTestMenuItem() {
		Command test = new Command() {
			public void execute() {
				mainPanel.test();
			}
		};
		MenuItem testItem = new MenuItem(menuMessages.test(), test);
		return testItem;
	}

	private MenuItem createUserProfileMenuItem() {
		Command showUserProfile = new Command() {
			public void execute() {
				mainPanel.showUserProfilePanel();
			}
		};
		MenuItem userProfileItem = new MenuItem(menuMessages.userProfile(), showUserProfile);
		return userProfileItem;
	}

	private MenuItem createUserManagementMenuItem() {
		Command showUserManagement = new Command() {
			public void execute() {
				mainPanel.showUserManagementPanel();
			}
		};
		MenuItem userManagementItem = new MenuItem(menuMessages.userManagement(), showUserManagement);
		return userManagementItem;
	}

	private MenuItem createArticleTypeMenuItem() {
		Command showArticleType = new Command() {
			public void execute() {
				mainPanel.showArticleTypePanel();
			}
		};
		MenuItem articleTypeItem = new MenuItem(menuMessages.articleType(), showArticleType);
		return articleTypeItem;
	}

	private MenuItem createStockMenuItem() {
		Command showStock = new Command() {
			public void execute() {
				mainPanel.showStockPanel();
			}
		};
		MenuItem stockItem = new MenuItem(menuMessages.stock(), showStock);
		return stockItem;
	}

	private MenuItem createSellStatisticMenuItem() {
		Command showStock = new Command() {
			public void execute() {
				mainPanel.showSellStatsticPanel();
			}
		};
		MenuItem sellStatisticItem = new MenuItem(menuMessages.sellStatistic(), showStock);
		return sellStatisticItem;
	}

	private MenuItem createPromoMenuItem() {
		Command showPromo = new Command() {
			public void execute() {
				mainPanel.showPromoPanel();
			}
		};
		MenuItem promoItem = new MenuItem(menuMessages.promo(), showPromo);
		return promoItem;
	}

	private MenuItem createPriceChangeMenuItem() {
		Command showPriceChange = new Command() {
			public void execute() {
				mainPanel.showPriceChangePanel();
			}
		};
		priceChangeItem = new MenuItem(menuMessages.priceChanges(0), showPriceChange);
		return priceChangeItem;
	}

	private MenuItem createSellArticleMenuItem() {
		Command sellArticle = new Command() {
			public void execute() {
				Xfashion.eventBus.fireEvent(new RequestOpenSellPopupEvent());
			}
		};
		MenuItem sellArticleItem = new MenuItem(menuMessages.sellArticle(), sellArticle);
		sellArticleItem.setTitle(menuMessages.sellArticleHint());
		return sellArticleItem;
	}
	
	private void registerForEvents() {
		Xfashion.eventBus.addHandler(LoginEvent.TYPE, this);
		Xfashion.eventBus.addHandler(PriceChangesUpdatedEvent.TYPE, this);
	}
	
}
