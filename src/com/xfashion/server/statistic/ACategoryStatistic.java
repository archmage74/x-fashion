package com.xfashion.server.statistic;

import com.google.appengine.api.datastore.Key;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.statistic.CategoryStatisticDTO;

public abstract class ACategoryStatistic implements IStatisticDetail {

	public abstract Key getKey();

	public abstract String getCategory();

	public abstract void setCategory(String category);

	public abstract Integer getPieces();

	public abstract void setPieces(Integer pieces);

	public abstract Integer getTurnover();

	public abstract void setTurnover(Integer turnover);

	public abstract Integer getProfit();

	public abstract void setProfit(Integer profit);

	public ACategoryStatistic() {
		setPieces(0);
		setTurnover(0);
		setProfit(0);
	}

	@Override
	public void initFromSoldArticleDTO(SoldArticleDTO soldArticleDTO) {
		setCategory(soldArticleDTO.getCategory());
	}

	@Override
	public boolean isRelevant(SoldArticleDTO soldArticle) {
		return true;
	}
	
	@Override
	public boolean matchesStatistic(SoldArticleDTO soldArticle) {
		return getCategory() != null && getCategory().equals(soldArticle.getCategory());
	}
	
	@Override
	public void addToStatistic(SoldArticleDTO soldArticleDTO) {
//		System.out.println("CategoryStatistic: add() called, pieces = " + getPieces());
		setPieces(getPieces() + soldArticleDTO.getAmount());
		setTurnover(getTurnover() + soldArticleDTO.getSellPrice());
		setProfit(getProfit() + soldArticleDTO.getSellPrice() - soldArticleDTO.getBuyPrice());
	}

	public CategoryStatisticDTO createDTO() {
		CategoryStatisticDTO dto = new CategoryStatisticDTO();
		dto.setCategory(getCategory());
		dto.setPieces(getPieces());
		dto.setTurnover(getTurnover());
		dto.setProfit(getProfit());
		return dto;
	}

}