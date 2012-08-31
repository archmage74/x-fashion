package com.xfashion.server.statistic;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;
import com.xfashion.shared.SoldArticleDTO;

@PersistenceCapable
public class DaySellStatistic {

	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	/** Calendar.DATE / .WEEK_OF_YEAR / .MONTH / .YEAR */
	private int periodType;

	/** start-date when the respective period starts */
	private Date startDate;

	private Integer pieces;
	
	private Integer turnover;
	
	private Integer profit;

	public DaySellStatistic() {
		init(new Date());
	}
	
	public DaySellStatistic(Date startDate) {
		init(startDate);
	}

	private void init(Date date) {
		GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("Europe/Vienna"));
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);
		this.startDate = gc.getTime();
		this.pieces = new Integer(0);
		this.turnover = new Integer(0);
		this.profit = new Integer(0);
	}
	
	public int getPeriodType() {
		return periodType;
	}

	public void setPeriodType(int periodType) {
		this.periodType = periodType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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

	public Key getKey() {
		return key;
	}

	public void addSoldArticle(SoldArticleDTO soldArticle) {
		
	}
	
}
