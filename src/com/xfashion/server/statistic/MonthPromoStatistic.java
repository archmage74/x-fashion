package com.xfashion.server.statistic;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class MonthPromoStatistic extends APromoStatistic {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	private String promoKey;
	
	private Integer pieces;
	
	private Integer turnover;
	
	private Integer profit;

	@Override
	public Key getKey() {
		return key;
	}

	@Override
	public String getPromoKey() {
		return promoKey;
	}

	@Override
	public void setPromoKey(String promoKey) {
		this.promoKey = promoKey;
	}

	@Override
	public Integer getPieces() {
		return pieces;
	}

	@Override
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

	@Override
	public Integer getTurnover() {
		return turnover;
	}

	@Override
	public void setTurnover(Integer turnover) {
		this.turnover = turnover;
	}

	@Override
	public Integer getProfit() {
		return profit;
	}

	@Override
	public void setProfit(Integer profit) {
		this.profit = profit;
	}

}
