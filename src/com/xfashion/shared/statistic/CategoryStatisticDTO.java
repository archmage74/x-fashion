package com.xfashion.shared.statistic;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CategoryStatisticDTO implements IsSerializable {

	private String category;
	
	private Integer pieces;

	private Integer turnover;
	
	private Integer profit;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getPieces() {
		return pieces;
	}

	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

	public Integer getTurnover() {
		return turnover;
	}

	public void setTurnover(Integer turnover) {
		this.turnover = turnover;
	}

	public Integer getProfit() {
		return profit;
	}

	public void setProfit(Integer profit) {
		this.profit = profit;
	}
	
}
