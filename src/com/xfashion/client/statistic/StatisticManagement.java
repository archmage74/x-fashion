package com.xfashion.client.statistic;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleFilterProvider;
import com.xfashion.client.statistic.event.AddMoreSellStatisticEvent;
import com.xfashion.client.statistic.event.AddMoreSellStatisticHandler;
import com.xfashion.client.statistic.event.SelectSellStatisticEvent;
import com.xfashion.client.statistic.event.SelectSellStatisticHandler;
import com.xfashion.client.statistic.event.ShowAllDetailsStatisticEvent;
import com.xfashion.client.statistic.event.ShowAllDetailsStatisticHandler;
import com.xfashion.client.statistic.event.ShowCategoriesDetailStatisticEvent;
import com.xfashion.client.statistic.event.ShowCategoriesDetailStatisticHandler;
import com.xfashion.client.statistic.event.ShowDayStatisticEvent;
import com.xfashion.client.statistic.event.ShowDayStatisticHandler;
import com.xfashion.client.statistic.event.ShowMonthStatisticEvent;
import com.xfashion.client.statistic.event.ShowMonthStatisticHandler;
import com.xfashion.client.statistic.event.ShowPromosDetailStatisticEvent;
import com.xfashion.client.statistic.event.ShowPromosDetailStatisticHandler;
import com.xfashion.client.statistic.event.ShowSizesDetailStatisticEvent;
import com.xfashion.client.statistic.event.ShowSizesDetailStatisticHandler;
import com.xfashion.client.statistic.event.ShowTopDetailStatisticEvent;
import com.xfashion.client.statistic.event.ShowTopDetailStatisticHandler;
import com.xfashion.client.statistic.event.ShowWeekStatisticEvent;
import com.xfashion.client.statistic.event.ShowWeekStatisticHandler;
import com.xfashion.client.statistic.event.ShowYearStatisticEvent;
import com.xfashion.client.statistic.event.ShowYearStatisticHandler;
import com.xfashion.client.statistic.sort.CategoryStatisticComparator;
import com.xfashion.client.statistic.sort.PromoStatisticComparator;
import com.xfashion.client.statistic.sort.SizeStatisticComparator;
import com.xfashion.client.statistic.sort.TopStatisticComparator;
import com.xfashion.shared.statistic.CategoryStatisticDTO;
import com.xfashion.shared.statistic.DaySellStatisticDTO;
import com.xfashion.shared.statistic.IDetailStatistic;
import com.xfashion.shared.statistic.MonthSellStatisticDTO;
import com.xfashion.shared.statistic.PromoStatisticDTO;
import com.xfashion.shared.statistic.SellStatisticDTO;
import com.xfashion.shared.statistic.SizeStatisticDTO;
import com.xfashion.shared.statistic.TopStatisticDTO;
import com.xfashion.shared.statistic.WeekSellStatisticDTO;
import com.xfashion.shared.statistic.YearSellStatisticDTO;

public class StatisticManagement implements ShowDayStatisticHandler, ShowWeekStatisticHandler, ShowMonthStatisticHandler, ShowYearStatisticHandler,
		ShowSizesDetailStatisticHandler, ShowCategoriesDetailStatisticHandler, ShowPromosDetailStatisticHandler, ShowTopDetailStatisticHandler,
		ShowAllDetailsStatisticHandler, SelectSellStatisticHandler, AddMoreSellStatisticHandler {

	private static final int PERIOD_PAGE_SIZE = 5;
	protected StatisticPanel statisticPanel;
	protected Panel panel;

	protected ListDataProvider<SellStatisticDTO> periodProvider;
	protected ListDataProvider<SizeStatisticDTO> sizeProvider;
	protected ListDataProvider<CategoryStatisticDTO> categoryProvider;
	protected ListDataProvider<PromoStatisticDTO> promoProvider;
	protected ListDataProvider<TopStatisticDTO> topProvider;

	/** possible values: Calendar.DATE / WEEK_OF_YEAR / MONTH / YEAR */
	protected int statisticPeriodType = Calendar.DATE;
	protected Class<? extends IDetailStatistic> currentDetailType = null;
	protected SellStatisticDTO currentSellStatistic = null;

	protected SizeStatisticComparator sizeComparator;
	protected TopStatisticComparator topComparator;
	protected CategoryStatisticComparator categoryComparator;
	protected PromoStatisticComparator promoComparator;

	protected StatisticServiceAsync statisticService = (StatisticServiceAsync) GWT.create(StatisticService.class);

	public StatisticManagement(ArticleFilterProvider articleProvider) {
		this.statisticPanel = new StatisticPanel(articleProvider);
		this.periodProvider = new ListDataProvider<SellStatisticDTO>();
		this.sizeProvider = new ListDataProvider<SizeStatisticDTO>();
		this.categoryProvider = new ListDataProvider<CategoryStatisticDTO>();
		this.promoProvider = new ListDataProvider<PromoStatisticDTO>();
		this.topProvider = new ListDataProvider<TopStatisticDTO>();
		this.sizeComparator = new SizeStatisticComparator(articleProvider.getSizeProvider());
		this.topComparator = new TopStatisticComparator();
		this.categoryComparator = new CategoryStatisticComparator(articleProvider.getCategoryProvider());
		this.promoComparator = new PromoStatisticComparator();
		
		registerForEvents();
	}

	public Panel getPanel() {
		if (panel == null) {
			panel = statisticPanel.createPanel(periodProvider);
		}
		refresh();
		return panel;
	}

	public void refresh() {
		loadStatistic();
	}

	@Override
	public void onShowDayStatistic(ShowDayStatisticEvent event) {
		clearDetailSelection();
		statisticPeriodType = Calendar.DATE;
		loadStatistic();
	}

	@Override
	public void onShowWeekStatistic(ShowWeekStatisticEvent event) {
		clearDetailSelection();
		statisticPeriodType = Calendar.WEEK_OF_YEAR;
		loadStatistic();
	}

	@Override
	public void onShowMonthStatistic(ShowMonthStatisticEvent event) {
		clearDetailSelection();
		statisticPeriodType = Calendar.MONTH;
		loadStatistic();
	}

	@Override
	public void onShowYearStatistic(ShowYearStatisticEvent event) {
		clearDetailSelection();
		statisticPeriodType = Calendar.YEAR;
		loadStatistic();
	}

	@Override
	public void onShowSizesDetailStatistic(ShowSizesDetailStatisticEvent event) {
		currentDetailType = SizeStatisticDTO.class;
		loadSizes();
	}

	@Override
	public void onShowCategoriesDetailStatistic(ShowCategoriesDetailStatisticEvent event) {
		currentDetailType = CategoryStatisticDTO.class;
		loadCategories();
	}

	@Override
	public void onShowPromosDetailStatistic(ShowPromosDetailStatisticEvent event) {
		currentDetailType = PromoStatisticDTO.class;
		loadPromos();
	}

	@Override
	public void onShowTopDetailStatistic(ShowTopDetailStatisticEvent event) {
		currentDetailType = TopStatisticDTO.class;
		loadTop();
	}

	@Override
	public void onShowAllDetailsStatistic(ShowAllDetailsStatisticEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSelectSellStatistic(SelectSellStatisticEvent event) {
		currentSellStatistic = event.getSellStatistic();
		loadDetailStatistic();
	}

	@Override
	public void onAddMoreSellStatistic(AddMoreSellStatisticEvent event) {
		addStatistic();
	}
	
	private void clearDetailSelection() {
		currentSellStatistic = null;
		currentDetailType = null;
		statisticPanel.clearDetailStatistic();
	}
	
	private void loadStatistic() {
		periodProvider.getList().clear();
		statisticPanel.setPeriodType(statisticPeriodType);
		statisticPanel.setAllStatisticLoaded(false);
		addStatistic();
	}

	private void addStatistic() {
		if (statisticPeriodType == Calendar.DATE) {
			loadDayCommonStatistic();
		} else if (statisticPeriodType == Calendar.WEEK_OF_YEAR) {
			loadWeekCommonStatistic();
		} else if (statisticPeriodType == Calendar.MONTH) {
			loadMonthCommonStatistic();
		} else if (statisticPeriodType == Calendar.YEAR) {
			loadYearCommonStatistic();
		}
	}

	private void loadDetailStatistic() {
		if (currentDetailType == null) {
			return ;
		}else if (currentDetailType.equals(SizeStatisticDTO.class)) {
			loadSizes();
		} else if (currentDetailType.equals(CategoryStatisticDTO.class)) {
			loadCategories();
		} else if (currentDetailType.equals(PromoStatisticDTO.class)) {
			loadPromos();
		} else if (currentDetailType.equals(TopStatisticDTO.class)) {
			loadTop();
		}
	}

	private void loadSizes() {
		if (currentSellStatistic == null) {
			return;
		}
		AsyncCallback<List<SizeStatisticDTO>> callback = new AsyncCallback<List<SizeStatisticDTO>>() {
			@Override
			public void onSuccess(List<SizeStatisticDTO> result) {
				showSizes(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		statisticService.readSizeStatistic(currentSellStatistic, callback);
	}

	private void showSizes(List<SizeStatisticDTO> sizes) {
		Collections.sort(sizes, sizeComparator);
		sizeProvider.getList().clear();
		sizeProvider.getList().addAll(sizes);
		statisticPanel.showSizeStatistic(sizeProvider);
	}

	private void loadCategories() {
		if (currentSellStatistic == null) {
			return;
		}
		AsyncCallback<List<CategoryStatisticDTO>> callback = new AsyncCallback<List<CategoryStatisticDTO>>() {
			@Override
			public void onSuccess(List<CategoryStatisticDTO> result) {
				showCategories(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		statisticService.readCategoryStatistic(currentSellStatistic, callback);
	}

	private void showCategories(List<CategoryStatisticDTO> categories) {
		addPercentToCategories(categories);
		Collections.sort(categories, categoryComparator);
		categoryProvider.getList().clear();
		categoryProvider.getList().addAll(categories);
		statisticPanel.showCategoryStatistic(categoryProvider);
	}

	private void addPercentToCategories(List<CategoryStatisticDTO> categories) {
		int profitSum = 0; 
		for (CategoryStatisticDTO item : categories) {
			profitSum += item.getPieces() * item.getProfit();
		}
		for (CategoryStatisticDTO item : categories) {
			item.setPercent(item.getPieces() * item.getProfit() * 100 / profitSum);
		}
		
	}

	private void loadPromos() {
		if (currentSellStatistic == null) {
			return;
		}
		AsyncCallback<List<PromoStatisticDTO>> callback = new AsyncCallback<List<PromoStatisticDTO>>() {
			@Override
			public void onSuccess(List<PromoStatisticDTO> result) {
				showPromos(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		statisticService.readPromoStatistic(currentSellStatistic, callback);
	}

	private void showPromos(List<PromoStatisticDTO> promos) {
		Collections.sort(promos, promoComparator);
		promoProvider.getList().clear();
		promoProvider.getList().addAll(promos);
		statisticPanel.showPromoStatistic(promoProvider);
	}

	private void loadTop() {
		if (currentSellStatistic == null) {
			return;
		}
		AsyncCallback<List<TopStatisticDTO>> callback = new AsyncCallback<List<TopStatisticDTO>>() {
			@Override
			public void onSuccess(List<TopStatisticDTO> result) {
				showTop(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		statisticService.readTopStatistic(currentSellStatistic, callback);
	}

	private void showTop(List<TopStatisticDTO> top) {
		Collections.sort(top, topComparator);
		topProvider.getList().clear();
		topProvider.getList().addAll(top);
		statisticPanel.showTopStatistic(topProvider);
	}

	private void loadDayCommonStatistic() {
		AsyncCallback<List<DaySellStatisticDTO>> callback = new AsyncCallback<List<DaySellStatisticDTO>>() {
			@Override
			public void onSuccess(List<DaySellStatisticDTO> result) {
				addSellStatistic(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		int currentAmount = periodProvider.getList().size();
		statisticService.readCommonDaySellStatistic(currentAmount, currentAmount + PERIOD_PAGE_SIZE, callback);
	}

	private void loadWeekCommonStatistic() {
		AsyncCallback<List<WeekSellStatisticDTO>> callback = new AsyncCallback<List<WeekSellStatisticDTO>>() {
			@Override
			public void onSuccess(List<WeekSellStatisticDTO> result) {
				addSellStatistic(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		int currentAmount = periodProvider.getList().size();
		statisticService.readCommonWeekSellStatistic(currentAmount, currentAmount + PERIOD_PAGE_SIZE, callback);
	}

	private void loadMonthCommonStatistic() {
		AsyncCallback<List<MonthSellStatisticDTO>> callback = new AsyncCallback<List<MonthSellStatisticDTO>>() {
			@Override
			public void onSuccess(List<MonthSellStatisticDTO> result) {
				addSellStatistic(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		int currentAmount = periodProvider.getList().size();
		statisticService.readCommonMonthSellStatistic(currentAmount, currentAmount + PERIOD_PAGE_SIZE, callback);
	}

	private void loadYearCommonStatistic() {
		AsyncCallback<List<YearSellStatisticDTO>> callback = new AsyncCallback<List<YearSellStatisticDTO>>() {
			@Override
			public void onSuccess(List<YearSellStatisticDTO> result) {
				addSellStatistic(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		int currentAmount = periodProvider.getList().size();
		statisticService.readCommonYearSellStatistic(currentAmount, currentAmount + PERIOD_PAGE_SIZE, callback);
	}

	protected <T extends SellStatisticDTO> void addSellStatistic(List<T> result) {
		if (result.size() == 0) {
			statisticPanel.setAllStatisticLoaded(true);
		}
		periodProvider.getList().addAll(result);
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(ShowDayStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowWeekStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowMonthStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowYearStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowSizesDetailStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowCategoriesDetailStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowPromosDetailStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowTopDetailStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowAllDetailsStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(SelectSellStatisticEvent.TYPE, this);
		Xfashion.eventBus.addHandler(AddMoreSellStatisticEvent.TYPE, this);
	}
}
