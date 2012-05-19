package com.xfashion.client.at.bulk;

import java.util.ArrayList;
import java.util.List;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleTypeDTO;

public class UpdateArticleTypesEvent extends Event<UpdateArticleTypesHandler> {
	
	public static Type<UpdateArticleTypesHandler> TYPE = new Type<UpdateArticleTypesHandler>();
	
	private List<ArticleTypeDTO> articleTypes;

	public UpdateArticleTypesEvent(ArticleTypeDTO articleType) {
		articleTypes = new ArrayList<ArticleTypeDTO>();
		articleTypes.add(articleType);
	}
	
	public UpdateArticleTypesEvent(List<ArticleTypeDTO> articleTypes) {
		this.articleTypes = new ArrayList<ArticleTypeDTO>(articleTypes);
	}
	
	@Override
	public Type<UpdateArticleTypesHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateArticleTypesHandler handler) {
		handler.onUpdateArticleTypes(this);
	}

	public List<ArticleTypeDTO> getArticleTypes() {
		return articleTypes;
	}

}
