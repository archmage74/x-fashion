package com.xfashion.client.at.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleTypeDTO;

public class DeleteArticleTypeEvent extends Event<DeleteArticleTypeHandler> {
	
	public static Type<DeleteArticleTypeHandler> TYPE = new Type<DeleteArticleTypeHandler>();

	ArticleTypeDTO articleType;

	public DeleteArticleTypeEvent(ArticleTypeDTO articleType) {
		this.articleType = articleType;
	}
	
	public ArticleTypeDTO getArticleType() {
		return articleType;
	}
	
	@Override
	public Type<DeleteArticleTypeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeleteArticleTypeHandler handler) {
		handler.onDeleteArticleType(this);
	}

}
