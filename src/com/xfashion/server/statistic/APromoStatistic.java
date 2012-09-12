package com.xfashion.server.statistic;

import com.google.appengine.api.datastore.Key;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.statistic.PromoStatisticDTO;

public abstract class APromoStatistic implements IStatisticDetail {

	public abstract String getPromoKey();

	public abstract void setPromoKey(String promoKey);

	public abstract Integer getPieces();

	public abstract void setPieces(Integer pieces);

	public abstract Integer getTurnover();

	public abstract void setTurnover(Integer turnover);

	public abstract Integer getProfit();

	public abstract void setProfit(Integer profit);

	public abstract Key getKey();

	public APromoStatistic() {
		setPieces(0);
		setTurnover(0);
		setProfit(0);
	}
	
	@Override
	public void initFromSoldArticleDTO(SoldArticleDTO soldArticleDTO) {
		setPromoKey(soldArticleDTO.getPromoKey());
	}

	@Override
	public boolean isRelevant(SoldArticleDTO soldArticle) {
		return soldArticle.getPromoKey() != null; 
	}
	
	@Override
	public boolean matchesStatistic(SoldArticleDTO soldArticle) {
		return getPromoKey() != null && getPromoKey().equals(soldArticle.getCategory());
	}
	
	@Override
	public void addToStatistic(SoldArticleDTO soldArticleDTO) {
		setPieces(getPieces() + soldArticleDTO.getAmount());
		setTurnover(getTurnover() + soldArticleDTO.getSellPrice());
		setProfit(getProfit() + soldArticleDTO.getSellPrice() - soldArticleDTO.getBuyPrice());
	}
	
	public PromoStatisticDTO createDTO() {
		PromoStatisticDTO dto = new PromoStatisticDTO();
		dto.setPromoKeyString(getPromoKey());
		dto.setPieces(getPieces());
		dto.setTurnover(getTurnover());
		dto.setProfit(getProfit());
		return dto;
	}
}