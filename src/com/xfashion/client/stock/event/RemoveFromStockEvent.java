package com.xfashion.client.stock.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleTypeDTO;

public class RemoveFromStockEvent extends Event<RemoveFromStockHandler> {

	public static Type<RemoveFromStockHandler> TYPE = new Type<RemoveFromStockHandler>();
	
	protected ArticleTypeDTO articleType;

	public RemoveFromStockEvent(ArticleTypeDTO articleType) {
		this.articleType = articleType;
	}
	
	public ArticleTypeDTO getArticleType() {
		return articleType;
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
