package com.xfashion.client.stock;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.xfashion.shared.SoldArticleDTO;

public class RemoveArticleFromSellListClickHandler implements ClickHandler {

	protected SoldArticleDTO sellArticle;
	protected SellFromStockPopup sellFromStockPopup;
	
	public RemoveArticleFromSellListClickHandler(SellFromStockPopup sellFromStockPopup, SoldArticleDTO sellArticle) {
		this.sellArticle = sellArticle;
		this.sellFromStockPopup = sellFromStockPopup;
	}

	@Override
	public void onClick(ClickEvent event) {
		sellFromStockPopup.removeArticle(sellArticle);
	}

	public SoldArticleDTO getSellArticle() {
		return sellArticle;
	}

}
