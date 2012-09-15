package com.xfashion.shared.statistic;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PromoStatisticDTO implements IDetailStatistic, IsSerializable {

	private String promoKeyString;
	
	private Integer pieces;

	private Integer turnover;
	
	private Integer profit;

	public String getPromoKeyString() {
		return promoKeyString;
	}

	public void setPromoKeyString(String promoKeyString) {
		this.promoKeyString = promoKeyString;
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
