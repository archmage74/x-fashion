package com.xfashion.client.stock.event;

import java.util.Collection;
import java.util.HashSet;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.SoldArticleDTO;

public class SellFromStockEvent extends Event<SellFromStockHandler> {

	public static Type<SellFromStockHandler> TYPE = new Type<SellFromStockHandler>();
	
	Collection<SoldArticleDTO> articles;

	public SellFromStockEvent(Collection<SoldArticleDTO> articles) {
		this.articles = new HashSet<SoldArticleDTO>();
		this.articles.addAll(articles);
	}
	
	public Collection<SoldArticleDTO> getArticles() {
		return articles;
	}
	
	@Override
	public Type<SellFromStockHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SellFromStockHandler handler) {
		handler.onSellFromStock(this);
	}

}
