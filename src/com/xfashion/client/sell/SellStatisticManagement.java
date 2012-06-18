package com.xfashion.client.sell;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.SoldArticleDTO;

public class SellStatisticManagement {

	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);

	private SellStatisticPanel sellStatisticPanel;
	private Panel panel;
	
	private List<SoldArticleDTO> soldArticles;
		
	TextMessages textMessages;

	public SellStatisticManagement() {
		textMessages = GWT.create(TextMessages.class);
		this.sellStatisticPanel = new SellStatisticPanel();
		soldArticles = new ArrayList<SoldArticleDTO>();
		registerForEvents();
	}

	/*
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
*/
	public Panel getPanel() {
		if (panel == null) {
			panel = sellStatisticPanel.createPanel();
		}
		if (soldArticles.size() == 0) {
			readSoldArticles();
		}
		return panel;
	}

	private void readSoldArticles() {
		AsyncCallback<List<SoldArticleDTO>> callback = new AsyncCallback<List<SoldArticleDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(List<SoldArticleDTO> result) {
				addSoldArticles(result);
			}
		};
		userService.readSoldArticles(new Date(), callback);
	}
	
	protected void addSoldArticles(List<SoldArticleDTO> result) {
		soldArticles.addAll(result);
		sellStatisticPanel.addSoldArticles(result);
	}

	private void registerForEvents() {

	}

}
