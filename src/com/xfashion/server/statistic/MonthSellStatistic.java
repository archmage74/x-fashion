package com.xfashion.server.statistic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.statistic.MonthSellStatisticDTO;

@PersistenceCapable
public class MonthSellStatistic extends SellStatistic {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	/** start-date of the statistic period */
	protected Date startDate;

	protected Integer pieces;
	
	protected Integer turnover;
	
	protected Integer profit;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "key asc"))
	private List<MonthSizeStatistic> sizeStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "key asc"))
	private List<MonthCategoryStatistic> categoryStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "key asc"))
	private List<MonthPromoStatistic> promoStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "key asc"))
	private List<MonthTopStatistic> topStatistics;

	public MonthSellStatistic() {
		super();
		initLists();
	}
	
	public MonthSellStatistic(Date startDate) {
		super(startDate);
		initLists();
	}

	public Key getKey() {
		return key;
	}

	@Override
	public String getKeyString() {
		return KeyFactory.keyToString(key);
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
	public List<MonthSizeStatistic> getSizeStatistics() {
		return sizeStatistics;
	}

	public void setSizeStatistics(List<MonthSizeStatistic> sizeStatistics) {
		this.sizeStatistics = sizeStatistics;
	}

	public List<MonthCategoryStatistic> getCategoryStatistics() {
		return categoryStatistics;
	}

	public void setCategoryStatistics(List<MonthCategoryStatistic> categoryStatistics) {
		this.categoryStatistics = categoryStatistics;
	}

	public List<MonthPromoStatistic> getPromoStatistics() {
		return promoStatistics;
	}

	public void setPromoStatistics(List<MonthPromoStatistic> promoStatistics) {
		this.promoStatistics = promoStatistics;
	}

	public List<MonthTopStatistic> getTopStatistics() {
		return topStatistics;
	}

	public void setTopStatistics(List<MonthTopStatistic> topStatistics) {
		this.topStatistics = topStatistics;
	}
	
	public MonthSellStatisticDTO createDTO() {
		MonthSellStatisticDTO dto = new MonthSellStatisticDTO();
		fillDTO(dto);
		return dto;
	}

	@Override
	protected void dayToPeriodStart(GregorianCalendar gc) {
		gc.set(Calendar.DAY_OF_MONTH, 1);		
	}
	
	@Override
	protected void addPeriod(GregorianCalendar gc) {
		gc.add(Calendar.MONTH, 1);
	}
	
	private void initLists() {
		setSizeStatistics(new ArrayList<MonthSizeStatistic>());
		setCategoryStatistics(new ArrayList<MonthCategoryStatistic>());
		setPromoStatistics(new ArrayList<MonthPromoStatistic>());
		setTopStatistics(new ArrayList<MonthTopStatistic>());
	}

}
