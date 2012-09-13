package com.xfashion.client.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.statistic.StatisticService;
import com.xfashion.client.statistic.StatisticServiceAsync;

public class AdminPanel {

	StatisticServiceAsync statisticService = (StatisticServiceAsync) GWT.create(StatisticService.class);
	
	protected Panel panel;
	
	public AdminPanel() {
		
	}
	
	public Panel createPanel() {
		panel = new VerticalPanel();
		panel.add(createRewriteStatisticButton());
		return panel;
	}
	
	protected void rewriteStatistic() {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				Xfashion.fireError("Rewrite Statistic: success");
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError("Rewrite Statistic: error: " + caught);
			}
		};
		statisticService.rewriteStatistic(callback);
	}

	private Button createRewriteStatisticButton() {
		Button rewriteStatisticButton = new Button("rewrite statistic");
		rewriteStatisticButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				rewriteStatistic();
			}
		});
		return rewriteStatisticButton;
	}

}
