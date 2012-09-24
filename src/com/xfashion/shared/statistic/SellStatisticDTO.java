package com.xfashion.shared.statistic;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class SellStatisticDTO implements IsSerializable {

	protected String keyString;
	
	protected Date startDate;
	
	protected Integer pieces;
	
	protected Integer turnover;
	
	protected Integer profit;

	public abstract int getPeriodType();
	
	public String getKeyString() {
		return keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getAmount() {
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
