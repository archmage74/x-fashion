package com.xfashion.client.statistic;

import java.util.Calendar;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleFilterProvider;
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
import com.xfashion.shared.statistic.DaySellStatisticDTO;
import com.xfashion.shared.statistic.MonthSellStatisticDTO;
import com.xfashion.shared.statistic.SellStatisticDTO;
import com.xfashion.shared.statistic.WeekSellStatisticDTO;
import com.xfashion.shared.statistic.YearSellStatisticDTO;

public class StatisticManagement implements ShowDayStatisticHandler, ShowWeekStatisticHandler, ShowMonthStatisticHandler, ShowYearStatisticHandler,
	ShowSizesDetailStatisticHandler, ShowCategoriesDetailStatisticHandler, ShowPromosDetailStatisticHandler, ShowTopDetailStatisticHandler, 
	ShowAllDetailsStatisticHandler {

	protected StatisticPanel statisticPanel;
	protected Panel panel;

	ListDataProvider<SellStatisticDTO> periodProvider;
	
	/** possible values: Calendar.DATE / WEEK_OF_YEAR / MONTH / YEAR */
	protected int statisticPeriodType = Calendar.DATE;

	protected StatisticServiceAsync statisticService = (StatisticServiceAsync) GWT.create(StatisticService.class);

	public StatisticManagement(ArticleFilterProvider articleProvider) {
		this.statisticPanel = new StatisticPanel(articleProvider);
		this.periodProvider = new ListDataProvider<SellStatisticDTO>(); 
		
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
		statisticPeriodType = Calendar.DATE;
		loadStatistic();
	}
	
	@Override
	public void onShowWeekStatistic(ShowWeekStatisticEvent event) {
		statisticPeriodType = Calendar.WEEK_OF_YEAR;
		loadStatistic();
	}
	
	@Override
	public void onShowMonthStatistic(ShowMonthStatisticEvent event) {
		statisticPeriodType = Calendar.MONTH;
		loadStatistic();
	}
	
	@Override
	public void onShowYearStatistic(ShowYearStatisticEvent event) {
		statisticPeriodType = Calendar.YEAR;
		loadStatistic();
	}
	
	@Override
	public void onShowSizesDetailStatistic(ShowSizesDetailStatisticEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onShowCategoriesDetailStatistic(ShowCategoriesDetailStatisticEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onShowPromosDetailStatistic(ShowPromosDetailStatisticEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onShowTopDetailStatistic(ShowTopDetailStatisticEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	private void loadStatistic() {
		periodProvider.getList().clear();
		statisticPanel.setPeriodType(statisticPeriodType);
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
	
	@Override
	public void onShowAllDetailsStatistic(ShowAllDetailsStatisticEvent event) {
		// TODO Auto-generated method stub
		
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
		statisticService.readCommonDaySellStatistic(0, 30, callback);
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
		periodProvider.getList().clear();
		statisticService.readCommonWeekSellStatistic(0, 30, callback);
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
		periodProvider.getList().clear();
		statisticService.readCommonMonthSellStatistic(0, 30, callback);
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
		periodProvider.getList().clear();
		statisticService.readCommonYearSellStatistic(0, 30, callback);
	}

	protected <T extends SellStatisticDTO> void addSellStatistic(List<T> result) {
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
	}
}
