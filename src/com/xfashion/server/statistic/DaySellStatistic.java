package com.xfashion.server.statistic;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.xfashion.shared.SoldArticleDTO;

@PersistenceCapable
public class DaySellStatistic {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	/** start-date of the statistic period */
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

	public void add(SoldArticleDTO soldArticle) {
		pieces += soldArticle.getAmount();
		turnover += soldArticle.getSellPrice();
		profit += soldArticle.getSellPrice() - soldArticle.getBuyPrice();
	}

	public boolean isWithinPeriod(Date sellDate) {
		long check = sellDate.getTime();
		GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("Europe/Vienna"));
		gc.setTime(startDate);
		long start = gc.getTime().getTime();
		gc.add(Calendar.DAY_OF_MONTH, 1);
		long end = gc.getTime().getTime();
		if (start <= check && end > check) {
			return true;
		} else {
			return false;
		}
	}
	
}
