package com.xfashion.shared.statistic;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CategoryStatisticDTO implements IDetailStatistic, IsSerializable {

	private String category;
	
	private Integer pieces;

	private Integer turnover;
	
	private Integer profit;
	
	private Integer percent;

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

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}
	
}
