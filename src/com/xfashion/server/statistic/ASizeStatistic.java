package com.xfashion.server.statistic;

import com.google.appengine.api.datastore.Key;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.statistic.SizeStatisticDTO;

public abstract class ASizeStatistic implements IStatisticDetail {

	public abstract Key getKey();

	public abstract String getSize();

	public abstract void setSize(String size);

	public abstract Integer getPieces();

	public abstract void setPieces(Integer pieces);

	public ASizeStatistic() {
		setPieces(0);
	}
	
	@Override
	public void initFromSoldArticleDTO(SoldArticleDTO soldArticleDTO) {
		setSize(soldArticleDTO.getSize());
	}

	@Override
	public boolean isRelevant(SoldArticleDTO soldArticle) {
		return true;
	}
	
	@Override
	public boolean matchesStatistic(SoldArticleDTO soldArticle) {
		return getSize() != null && getSize().equals(soldArticle.getSize());
	}

	@Override
	public void addToStatistic(SoldArticleDTO soldArticleDTO) {
		setPieces(getPieces() + soldArticleDTO.getAmount());
	}
	
	public SizeStatisticDTO createDTO() {
		SizeStatisticDTO dto = new SizeStatisticDTO();
		dto.setSize(getSize());
		dto.setPieces(getPieces());
		return dto;
	}

}