package com.xfashion.client.at.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleTypeDTO;

public class CreateArticleTypeEvent extends Event<CreateArticleTypeHandler> {
	
	public static Type<CreateArticleTypeHandler> TYPE = new Type<CreateArticleTypeHandler>();

	ArticleTypeDTO articleType;

	public CreateArticleTypeEvent(ArticleTypeDTO articleType) {
		this.articleType = articleType;
	}
	
	public ArticleTypeDTO getArticleType() {
		return articleType;
	}
	
	@Override
	public Type<CreateArticleTypeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreateArticleTypeHandler handler) {
		handler.onCreateArticleType(this);
	}

}
