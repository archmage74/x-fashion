package com.xfashion.client.statistic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.Formatter;
import com.xfashion.client.MainPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.event.ContentPanelResizeEvent;
import com.xfashion.client.event.ContentPanelResizeHandler;
import com.xfashion.client.protocols.event.ShowSellStatisticEvent;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.user.UserManagement;
import com.xfashion.client.user.UserService;
import com.xfashion.shared.ShopDTO;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.UserRole;
import com.xfashion.shared.statistic.SellStatisticDTO;

public class StatisticPanel implements ContentPanelResizeHandler {

	private static final int SOLD_ARTICLE_PANEL_WIDTH = 1200;
	
	protected List<ShopDTO> knownShops;
	protected IProvideArticleFilter filterProvider;

	protected HorizontalPanel headerPanel;
	protected VerticalPanel mainPanel;
	protected ListBox shopListBox;

	protected TextMessages textMessages;
	protected Formatter formatter;

	private ListBox sellPeriodListBox;

	public StatisticPanel(IProvideArticleFilter filterProvider) {
		this.knownShops = new ArrayList<ShopDTO>();
		this.textMessages = GWT.create(TextMessages.class);
		this.formatter = Formatter.getInstance();
		
		this.filterProvider = filterProvider;
		
		registerForEvents();
	}

	public Panel createPanel() {
		VerticalPanel panel = new VerticalPanel();
		panel.add(createHeaderPanel());
		panel.add(createStatisticPanel());
		return panel;
	}

	public void setUsers(Collection<UserDTO> users) {
		shopListBox.clear();
		knownShops.clear();
		shopListBox.addItem(textMessages.allShops());
		knownShops.add(new ShopDTO());
		for (UserDTO user : users) {
			if (!UserService.ROOT_USERNAME.equals(user.getUsername())) {
				shopListBox.addItem(user.getShop().getShortName());
				knownShops.add(user.getShop());
			}
		}
	}

	protected Panel createStatisticPanel() {
		HorizontalPanel hp = new HorizontalPanel();
		setHeight(MainPanel.contentPanelHeight);
		hp.add(createSellPeriodList());
		return hp;
	}

	@Override
	public void onContentPanelResize(ContentPanelResizeEvent event) {
		setHeight(event.getHeight());
	}
	
	public void setHeight(int height) {
		int panelHeight = height - 100;
		if (mainPanel != null) {
			mainPanel.setHeight(panelHeight + "px");
		}
	}

	private Panel createSellPeriodList() {
		mainPanel = new VerticalPanel();
		sellPeriodListBox = new ListBox();
		sellPeriodListBox.setVisibleItemCount(30);
		mainPanel.add(sellPeriodListBox);
		return mainPanel;
	}
	
	// TODO remove when cell listbox is replaced by celltable
	public <T extends SellStatisticDTO> void setEntries(List<T> entries) {
		sellPeriodListBox.clear();
		for (T entry : entries) {
			StringBuffer sb = new StringBuffer();
			sb.append(entry.getStartDate()).append(" - ");
			sb.append(entry.getAmount()).append(" - ");
			sb.append(entry.getTurnover()).append(" - ");
			sb.append(entry.getProfit());
			sellPeriodListBox.addItem(sb.toString());
		}
	}
	
	private Widget createShopList() {
		shopListBox = new ListBox();
		shopListBox.setVisibleItemCount(1);
		shopListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				selectShop(shopListBox.getSelectedIndex());
			}
		});
		return shopListBox;
	}

	private void selectShop(int shopIndex) {
		if (shopIndex == 0) {
			Xfashion.eventBus.fireEvent(new ShowSellStatisticEvent(null));
		} else {
			Xfashion.eventBus.fireEvent(new ShowSellStatisticEvent(knownShops.get(shopIndex)));
		}
	}

	protected HorizontalPanel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("contentHeader");
		headerPanel.setWidth(SOLD_ARTICLE_PANEL_WIDTH + "px");

		headerPanel.add(createHeaderLabel());
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		if (UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
			headerPanel.add(createShopList());
		}

		HorizontalPanel toolPanel = new HorizontalPanel();
		toolPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.add(toolPanel);

		return headerPanel;
	}

	private Label createHeaderLabel() {
		Label headerLabel = new Label(textMessages.statisticsHeader());
		headerLabel.setStyleName("contentHeaderLabel");
		return headerLabel;
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(ContentPanelResizeEvent.TYPE, this);
	}

}
