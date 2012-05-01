package com.xfashion.client.at;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleTypeDTO;

public class AddArticleEvent extends Event<AddArticleHandler> {

	public static Type<AddArticleHandler> TYPE = new Type<AddArticleHandler>();
	
	private ArticleTypeDTO articleType;
	
	public AddArticleEvent(ArticleTypeDTO articleType) {
		setArticleType(articleType);
	}
	
	@Override
	public Type<AddArticleHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(AddArticleHandler handler) {
		handler.onAddArticle(this);
	}

	public void setArticleType(ArticleTypeDTO articleType) {
		this.articleType = articleType;
	}

	public ArticleTypeDTO getArticleType() {
		return articleType;
	}
	
}
