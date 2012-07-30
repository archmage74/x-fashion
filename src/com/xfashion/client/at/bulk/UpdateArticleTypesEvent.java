package com.xfashion.client.at.bulk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.PriceChangeDTO;

public class UpdateArticleTypesEvent extends Event<UpdateArticleTypesHandler> {
	
	public static Type<UpdateArticleTypesHandler> TYPE = new Type<UpdateArticleTypesHandler>();
	
	private List<ArticleTypeDTO> articleTypes;
	
	private Collection<PriceChangeDTO> priceChanges;

	public UpdateArticleTypesEvent(ArticleTypeDTO articleType, PriceChangeDTO priceChange) {
		this.articleTypes = new ArrayList<ArticleTypeDTO>();
		if (articleType != null) {
			this.articleTypes.add(articleType);
		}
		this.priceChanges = new ArrayList<PriceChangeDTO>();
		if (priceChange != null) {
			this.priceChanges.add(priceChange);
		}
	}
	
	public UpdateArticleTypesEvent(List<ArticleTypeDTO> articleTypes, Collection<PriceChangeDTO> priceChanges) {
		this.articleTypes = new ArrayList<ArticleTypeDTO>(articleTypes);
		this.priceChanges = priceChanges;
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

	public Collection<PriceChangeDTO> getPriceChanges() {
		return priceChanges;
	}

}
