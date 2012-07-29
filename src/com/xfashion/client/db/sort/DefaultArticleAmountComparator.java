package com.xfashion.client.db.sort;

import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;

public class DefaultArticleAmountComparator implements IArticleAmountComparator {

	protected DefaultArticleTypeComparator articleTypeComparator;
	
	protected ArticleTypeDatabase articleTypeDatabase;
	
	public DefaultArticleAmountComparator(ArticleTypeDatabase articleTypeProvider) {
		this.articleTypeDatabase = articleTypeProvider;
		this.articleTypeComparator = new DefaultArticleTypeComparator();
		this.articleTypeComparator.setCategoryProvider(articleTypeDatabase.getCategoryProvider());
		this.articleTypeComparator.setBrandProvider(articleTypeDatabase.getBrandProvider());
		this.articleTypeComparator.setColorProvider(articleTypeDatabase.getColorProvider());
		this.articleTypeComparator.setSizeProvider(articleTypeDatabase.getSizeProvider());
	}

	@Override
	public int compare(ArticleAmountDTO o0, ArticleAmountDTO o1) {
		ArticleTypeDataProvider articleTypeProvider = articleTypeDatabase.getArticleTypeProvider();
		ArticleTypeDTO at0 = articleTypeProvider.resolveData(o0.getArticleTypeKey());
		ArticleTypeDTO at1 = articleTypeProvider.resolveData(o1.getArticleTypeKey());
		if (at0 == at1) {
			return 0;
		}
		if (at0 == null) {
			return 1;
		}
		if (at1 == null) {
			return -1;
		}
		return articleTypeComparator.compare(at0, at1);
	}
}
