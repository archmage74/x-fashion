package com.xfashion.shared.statistic;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TopStatisticDTO implements IDetailStatistic, IsSerializable {

	private String category;
	
	private String articleName;
	
	private Integer pieces;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public Integer getPieces() {
		return pieces;
	}

	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

}
