package com.xfashion.client.statistic;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleFilterProvider;
import com.xfashion.shared.statistic.DaySellStatisticDTO;

public class StatisticManagement {

	protected StatisticPanel statisticPanel;
	protected Panel panel;

	protected StatisticServiceAsync statisticService = (StatisticServiceAsync) GWT.create(StatisticService.class);

	public StatisticManagement(ArticleFilterProvider articleProvider) {
		this.statisticPanel = new StatisticPanel(articleProvider);
		registerForEvents();
	}
	
	public Panel getPanel() {
		if (panel == null) {
			panel = statisticPanel.createPanel();
		}
		refresh();
		return panel;
	}

	public void refresh() {
		addDayCommonStatistic();
	}
	
	private void addDayCommonStatistic() {
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

	protected void addSellStatistic(List<DaySellStatisticDTO> result) {
		statisticPanel.setEntries(result);
	}

	private void registerForEvents() {
		
	}
}
