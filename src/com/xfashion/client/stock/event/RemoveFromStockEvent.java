package com.xfashion.client.stock.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleTypeDTO;

public class RemoveFromStockEvent extends Event<RemoveFromStockHandler> {

	public static Type<RemoveFromStockHandler> TYPE = new Type<RemoveFromStockHandler>();
	
	protected ArticleTypeDTO articleType;
	
	protected int amount;

	public RemoveFromStockEvent(ArticleTypeDTO articleType) {
		this(articleType, 1);
	}
	
	public RemoveFromStockEvent(ArticleTypeDTO articleType, int amount) {
		this.articleType = articleType;
		this.amount = amount;
	}
	
	public ArticleTypeDTO getArticleType() {
		return articleType;
	}
	
	public int getAmount() {
		return amount;
	}

	@Override
	public Type<RemoveFromStockHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RemoveFromStockHandler handler) {
		handler.onRemoveFromStock(this);
	}

}
