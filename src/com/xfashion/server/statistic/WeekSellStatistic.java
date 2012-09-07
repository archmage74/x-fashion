package com.xfashion.server.statistic;

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
import com.xfashion.shared.WeekSellStatisticDTO;

@PersistenceCapable
public class WeekSellStatistic extends SellStatistic {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	/** start-date of the statistic period */
	protected Date startDate;

	protected Integer pieces;
	
	protected Integer turnover;
	
	protected Integer profit;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="pieces desc"))
	private List<SizeStatistic> sizeStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="pieces desc"))
	private List<CategoryStatistic> categoryStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="pieces desc"))
	private List<PromoStatistic> promoStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="pieces desc"))
	private List<TopStatistic> topStatistics;

	public WeekSellStatistic() {
		super();
	}
	
	public WeekSellStatistic(Date startDate) {
		super(startDate);
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
	public List<SizeStatistic> getSizeStatistics() {
		return sizeStatistics;
	}

	public void setSizeStatistics(List<SizeStatistic> sizeStatistics) {
		this.sizeStatistics = sizeStatistics;
	}

	public List<CategoryStatistic> getCategoryStatistics() {
		return categoryStatistics;
	}

	public void setCategoryStatistics(List<CategoryStatistic> categoryStatistics) {
		this.categoryStatistics = categoryStatistics;
	}

	public List<PromoStatistic> getPromoStatistics() {
		return promoStatistics;
	}

	public void setPromoStatistics(List<PromoStatistic> promoStatistics) {
		this.promoStatistics = promoStatistics;
	}

	public List<TopStatistic> getTopStatistics() {
		return topStatistics;
	}

	public void setTopStatistics(List<TopStatistic> topStatistics) {
		this.topStatistics = topStatistics;
	}

	public WeekSellStatisticDTO createDTO() {
		WeekSellStatisticDTO dto = new WeekSellStatisticDTO();
		fillDTO(dto);
		return dto;
	}

	@Override
	protected void dayToPeriodStart(GregorianCalendar gc) {
		gc.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);		
	}
	
	@Override
	protected void addPeriod(GregorianCalendar gc) {
		gc.add(Calendar.WEEK_OF_MONTH, 1);
	}

}
