package com.xfashion.client.stock.event;

import java.util.Collection;
import java.util.HashSet;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.notepad.ArticleAmountDTO;

public class SellFromStockEvent extends Event<SellFromStockHandler> {

	public static Type<SellFromStockHandler> TYPE = new Type<SellFromStockHandler>();
	
	Collection<ArticleAmountDTO> articles;

	public SellFromStockEvent(Collection<ArticleAmountDTO> articles) {
		this.articles = new HashSet<ArticleAmountDTO>();
		this.articles.addAll(articles);
	}
	
	public Collection<ArticleAmountDTO> getArticles() {
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
