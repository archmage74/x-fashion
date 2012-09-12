package com.xfashion.server.statistic;

import com.google.appengine.api.datastore.Key;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.statistic.TopStatisticDTO;

public abstract class ATopStatistic implements IStatisticDetail {

	public abstract Key getKey();

	public abstract String getCategory();

	public abstract void setCategory(String category);

	public abstract String getArticleName();

	public abstract void setArticleName(String articleName);

	public abstract Integer getPieces();

	public abstract void setPieces(Integer pieces);

	public ATopStatistic() {
		setPieces(0);
	}
	
	@Override
	public void initFromSoldArticleDTO(SoldArticleDTO soldArticleDTO) {
		setCategory(soldArticleDTO.getCategory());
		setArticleName(soldArticleDTO.getArticleName());
	}

	@Override
	public boolean isRelevant(SoldArticleDTO soldArticle) {
		return true;
	}
	
	@Override
	public boolean matchesStatistic(SoldArticleDTO soldArticle) {
		return getArticleName() != null 
				&& getCategory() != null 
				&& getArticleName().equals(soldArticle.getArticleName())
				&& getCategory().equals(soldArticle.getCategory());
	}

	@Override
	public void addToStatistic(SoldArticleDTO soldArticleDTO) {
		setPieces(getPieces() + soldArticleDTO.getAmount());
	}
	
	public TopStatisticDTO createDTO() {
		TopStatisticDTO dto = new TopStatisticDTO();
		dto.setCategory(getCategory());
		dto.setArticleName(getArticleName());
		dto.setPieces(getPieces());
		return dto;
	}

}
