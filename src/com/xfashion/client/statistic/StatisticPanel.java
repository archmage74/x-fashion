package com.xfashion.client.statistic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.xfashion.client.Formatter;
import com.xfashion.client.MainPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.at.price.GetSellPriceFromSoldArticleStrategy;
import com.xfashion.client.event.ContentPanelResizeEvent;
import com.xfashion.client.event.ContentPanelResizeHandler;
import com.xfashion.client.protocols.SoldArticleDataProvider;
import com.xfashion.client.protocols.SoldArticleTable;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.statistic.event.AddMoreSellStatisticDetailsEvent;
import com.xfashion.client.statistic.event.AddMoreSellStatisticEvent;
import com.xfashion.client.statistic.event.SelectShopSellStatisticEvent;
import com.xfashion.client.statistic.event.ShowAllDetailsStatisticEvent;
import com.xfashion.client.statistic.event.ShowCategoriesDetailStatisticEvent;
import com.xfashion.client.statistic.event.ShowDayStatisticEvent;
import com.xfashion.client.statistic.event.ShowMonthStatisticEvent;
import com.xfashion.client.statistic.event.ShowPromosDetailStatisticEvent;
import com.xfashion.client.statistic.event.ShowSizesDetailStatisticEvent;
import com.xfashion.client.statistic.event.ShowTopDetailStatisticEvent;
import com.xfashion.client.statistic.event.ShowWeekStatisticEvent;
import com.xfashion.client.statistic.event.ShowYearStatisticEvent;
import com.xfashion.client.statistic.render.CategoryStatisticTableProvider;
import com.xfashion.client.statistic.render.PromoStatisticTableProvider;
import com.xfashion.client.statistic.render.SizeStatisticTableProvider;
import com.xfashion.client.statistic.render.StatisticPeriodTableProvider;
import com.xfashion.client.statistic.render.TopStatisticTableProvider;
import com.xfashion.client.user.UserManagement;
import com.xfashion.client.user.UserService;
import com.xfashion.shared.ShopDTO;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.UserRole;
import com.xfashion.shared.statistic.CategoryStatisticDTO;
import com.xfashion.shared.statistic.PromoStatisticDTO;
import com.xfashion.shared.statistic.SellStatisticDTO;
import com.xfashion.shared.statistic.SizeStatisticDTO;
import com.xfashion.shared.statistic.TopStatisticDTO;

public class StatisticPanel implements ContentPanelResizeHandler, RowCountChangeEvent.Handler {

	private static final int STATISTIC_PANEL_WIDTH = 1200;

	private static final int SOLD_ARTICLE_ELEMENT_HEIGHT = 80;
	
	private static final int SELL_STATISTIC_ELEMENT_HEIGHT = 18;
	
	protected List<ShopDTO> knownShops;
	protected IProvideArticleFilter filterProvider;

	protected HorizontalPanel headerPanel;
	protected Panel mainPanel;
	protected ListBox shopListBox;
	protected ScrollPanel periodPanel;

	protected CellTable<SellStatisticDTO> periodTable;
	protected CellTable<SizeStatisticDTO> sizeTable;
	protected CellTable<CategoryStatisticDTO> categoryTable;
	protected CellTable<PromoStatisticDTO> promoTable;
	protected CellTable<TopStatisticDTO> topTable;
	protected CellTable<SoldArticleDTO> soldArticleTable;
	protected ScrollPanel soldArticlePanel;
	protected VerticalPanel detailListPanel;

	protected TextMessages textMessages;
	protected Formatter formatter;
	protected StatisticPeriodTableProvider tableProvider;
	protected SizeStatisticTableProvider sizeTableProvider;
	protected CategoryStatisticTableProvider categoryTableProvider;
	protected PromoStatisticTableProvider promoTableProvider;
	protected TopStatisticTableProvider topTableProvider;
	protected SoldArticleTable soldArticlePanelProvider;

	public StatisticPanel(IProvideArticleFilter filterProvider) {
		this.knownShops = new ArrayList<ShopDTO>();
		this.textMessages = GWT.create(TextMessages.class);
		this.formatter = Formatter.getInstance();

		this.tableProvider = new StatisticPeriodTableProvider();
		this.sizeTableProvider = new SizeStatisticTableProvider();
		this.categoryTableProvider = new CategoryStatisticTableProvider();
		this.promoTableProvider = new PromoStatisticTableProvider();
		this.topTableProvider = new TopStatisticTableProvider();
		this.soldArticlePanelProvider = new SoldArticleTable(filterProvider, new GetSellPriceFromSoldArticleStrategy());

		this.filterProvider = filterProvider;

		registerForEvents();
	}

	public Panel createPanel(ListDataProvider<SellStatisticDTO> periodProvider) {
		VerticalPanel panel = new VerticalPanel();
		panel.add(createHeaderPanel());
		panel.add(createStatisticPanel());
		setHeight(MainPanel.contentPanelHeight);

		periodProvider.addDataDisplay(periodTable);
		periodTable.addRowCountChangeHandler(this);

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

	public void setPeriodType(int statisticPeriodType) {
		tableProvider.changePeriodType(periodTable, statisticPeriodType);
	}

	public void clearDetailStatistic() {
		showDetailTable(null);
	}

	public void showSizeStatistic(ListDataProvider<SizeStatisticDTO> sizeProvider) {
		if (sizeTable == null) {
			sizeTable = sizeTableProvider.createTable();
			sizeProvider.addDataDisplay(sizeTable);
		}
		showDetailTable(sizeTable);
	}

	public void showCategoryStatistic(ListDataProvider<CategoryStatisticDTO> categoryProvider) {
		if (categoryTable == null) {
			categoryTable = categoryTableProvider.createTable();
			categoryProvider.addDataDisplay(categoryTable);
		}
		showDetailTable(categoryTable);
	}

	public void showPromoStatistic(ListDataProvider<PromoStatisticDTO> promoProvider) {
		if (promoTable == null) {
			promoTable = promoTableProvider.createTable();
			promoProvider.addDataDisplay(promoTable);
		}
		showDetailTable(promoTable);
	}

	public void showTopStatistic(ListDataProvider<TopStatisticDTO> topProvider) {
		if (topTable == null) {
			topTable = topTableProvider.createTable();
			topProvider.addDataDisplay(topTable);
		}
		showDetailTable(topTable);
	}

	public void showSoldArticles(SoldArticleDataProvider soldProvider) {
		if (soldArticlePanel == null) {
			soldArticlePanel = soldArticlePanelProvider.create(soldProvider);
			soldArticlePanel.setWidth("360px");
			soldArticleTable = soldArticlePanelProvider.getCellTable();
			soldArticleTable.addRowCountChangeHandler(this);
			soldArticlePanel.addScrollHandler(new ScrollHandler() {
				@Override
				public void onScroll(ScrollEvent event) {
					checkLoadMore();
				}
			});
			setHeight(MainPanel.contentPanelHeight);
		}
		showDetailTable(soldArticlePanel);
	}
	
	protected Panel createStatisticPanel() {
		mainPanel = new HorizontalPanel();
		setHeight(MainPanel.contentPanelHeight);
		mainPanel.add(createPeriodPanel());
		mainPanel.add(createDetailPanel());
		return mainPanel;
	}

	@Override
	public void onContentPanelResize(ContentPanelResizeEvent event) {
		setHeight(event.getHeight());
		checkLoadMore();
	}

	@Override
	public void onRowCountChange(RowCountChangeEvent event) {
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			public void execute() {
				checkLoadMore();
			}
		});
	}

	private void checkLoadMore() {
		// check if half there is at least 25% of the paging-size more data within the tables, if not load more data
		if (periodTable != null && periodTable.getRowCount() > 0 && 
				periodPanel.getVerticalScrollPosition() > periodPanel.getMaximumVerticalScrollPosition() - SELL_STATISTIC_ELEMENT_HEIGHT * StatisticManagement.PERIOD_PAGE_SIZE / 4) {
			fireLoadMoreSellStatisticEvent();
		}
		if (soldArticleTable != null && 
				soldArticleTable.getRowCount() > 0 && 
				soldArticlePanel.getVerticalScrollPosition() > soldArticlePanel.getMaximumVerticalScrollPosition() - SOLD_ARTICLE_ELEMENT_HEIGHT * StatisticManagement.ALL_DETAIL_PAGE_SIZE / 4) {
			fireLoadMoreSellStatisticDetailsEvent();
		}
	}

	private void fireLoadMoreSellStatisticEvent() {
		Xfashion.eventBus.fireEvent(new AddMoreSellStatisticEvent());
	}

	private void fireLoadMoreSellStatisticDetailsEvent() {
		Xfashion.eventBus.fireEvent(new AddMoreSellStatisticDetailsEvent());
	}

	public void setHeight(int height) {
		int panelHeight = height - 40;
		if (mainPanel != null) {
			mainPanel.setHeight(panelHeight + "px");
		}
		if (periodPanel != null) {
			periodPanel.setHeight(panelHeight - 50 + "px");
		}
		if (soldArticlePanel != null) {
			soldArticlePanel.setHeight(panelHeight - 50 + "px");
		}
	}

	private void showDetailTable(Widget table) {
		detailListPanel.clear();
		if (table != null) {
			detailListPanel.add(table);
		}
	}

	private Panel createPeriodPanel() {
		VerticalPanel vp = new VerticalPanel();
		vp.add(createPeriodSelectionPanel());
		vp.add(createSellPeriodTable());
		return vp;
	}

	private Panel createPeriodSelectionPanel() {
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(createDayButton());
		hp.add(createWeekButton());
		hp.add(createMonthButton());
		hp.add(createYearButton());
		return hp;
	}

	private Widget createDayButton() {
		Button dayButton = new Button(textMessages.dayButton());
		dayButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ShowDayStatisticEvent());
			}
		});
		return dayButton;
	}

	private Widget createWeekButton() {
		Button weekButton = new Button(textMessages.weekButton());
		weekButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ShowWeekStatisticEvent());
			}
		});
		return weekButton;
	}

	private Widget createMonthButton() {
		Button monthButton = new Button(textMessages.monthButton());
		monthButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ShowMonthStatisticEvent());
			}
		});
		return monthButton;
	}

	private Widget createYearButton() {
		Button yearButton = new Button(textMessages.yearButton());
		yearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ShowYearStatisticEvent());
			}
		});
		return yearButton;
	}

	private Panel createSellPeriodTable() {
		periodTable = tableProvider.createTable();
		periodPanel = new ScrollPanel(periodTable);
		periodPanel.setWidth("300px");
		periodPanel.setHeight("200px");
		periodPanel.addScrollHandler(new ScrollHandler() {
			@Override
			public void onScroll(ScrollEvent event) {
				checkLoadMore();
			}
		});
		return periodPanel;
	}

	private Panel createDetailPanel() {
		VerticalPanel vp = new VerticalPanel();
		vp.add(createDetailSelectionPanel());
		vp.add(createDetailListPanel());
		return vp;
	}

	private Widget createDetailSelectionPanel() {
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(createSizesButton());
		hp.add(createCategoriesButton());
		hp.add(createPromosButton());
		hp.add(createTopButton());
		hp.add(createAllDetailsButton());
		return hp;
	}

	private Widget createSizesButton() {
		Button sizesButton = new Button(textMessages.statisticSizes());
		sizesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ShowSizesDetailStatisticEvent());
			}
		});
		return sizesButton;
	}

	private Widget createCategoriesButton() {
		Button categoriesButton = new Button(textMessages.statisticCategories());
		categoriesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ShowCategoriesDetailStatisticEvent());
			}
		});
		return categoriesButton;
	}

	private Widget createPromosButton() {
		Button promosButton = new Button(textMessages.statisticPromos());
		promosButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ShowPromosDetailStatisticEvent());
			}
		});
		return promosButton;
	}

	private Widget createTopButton() {
		Button topButton = new Button(textMessages.statisticTop());
		topButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ShowTopDetailStatisticEvent());
			}
		});
		return topButton;
	}

	private Widget createAllDetailsButton() {
		Button allDetailsButton = new Button(textMessages.statisticAllDetails());
		allDetailsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ShowAllDetailsStatisticEvent());
			}
		});
		return allDetailsButton;
	}

	private Widget createDetailListPanel() {
		detailListPanel = new VerticalPanel();
		return detailListPanel;
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
			Xfashion.eventBus.fireEvent(new SelectShopSellStatisticEvent(null));
		} else {
			Xfashion.eventBus.fireEvent(new SelectShopSellStatisticEvent(knownShops.get(shopIndex)));
		}
	}

	protected HorizontalPanel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("contentHeader");
		headerPanel.setWidth(STATISTIC_PANEL_WIDTH + "px");

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

	@Override
	public String toString() {
		return "StatisticPanel { }";
	}

}
