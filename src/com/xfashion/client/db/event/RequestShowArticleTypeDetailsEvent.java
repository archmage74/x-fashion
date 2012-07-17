package com.xfashion.client.db.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleTypeDTO;

public class RequestShowArticleTypeDetailsEvent extends Event<RequestShowArticleTypeDetailsHandler> {
	
	public static Type<RequestShowArticleTypeDetailsHandler> TYPE = new Type<RequestShowArticleTypeDetailsHandler>();

	ArticleTypeDTO articleType;

	public RequestShowArticleTypeDetailsEvent(ArticleTypeDTO articleType) {
		this.articleType = articleType;
	}
	
	public ArticleTypeDTO getArticleType() {
		return articleType;
	}
	
	@Override
	public Type<RequestShowArticleTypeDetailsHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RequestShowArticleTypeDetailsHandler handler) {
		handler.onRequestShowArticleTypeDetails(this);
	}

}
