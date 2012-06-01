package com.xfashion.client.at;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleTypeDTO;

public class UpdateArticleTypeEvent extends Event<UpdateArticleTypeHandler> {
	
	public static Type<UpdateArticleTypeHandler> TYPE = new Type<UpdateArticleTypeHandler>();

	ArticleTypeDTO articleType;

	public UpdateArticleTypeEvent(ArticleTypeDTO articleType) {
		this.articleType = articleType;
	}
	
	public ArticleTypeDTO getArticleType() {
		return articleType;
	}
	
	@Override
	public Type<UpdateArticleTypeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateArticleTypeHandler handler) {
		handler.onUpdateArticleType(this);
	}

}
