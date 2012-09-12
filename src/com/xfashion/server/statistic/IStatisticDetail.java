package com.xfashion.server.statistic;

import com.xfashion.shared.SoldArticleDTO;

public interface IStatisticDetail {

	/**
	 * Determines if the given sold article is relevant for this type of statistic-detail. 
	 * E.g. sold articles where no promo code has been applied are not taken into account for any promo statistic
	 * @param soldArticle
	 * @return
	 */
	boolean isRelevant(SoldArticleDTO soldArticle);
	
	boolean matchesStatistic(SoldArticleDTO soldArticle);
	
	void initFromSoldArticleDTO(SoldArticleDTO soldArticleDTO);
	
	void addToStatistic(SoldArticleDTO soldArticleDTO);

}
