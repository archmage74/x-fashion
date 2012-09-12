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
import com.xfashion.shared.statistic.YearSellStatisticDTO;

@PersistenceCapable
public class YearSellStatistic extends SellStatistic {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	/** start-date of the statistic period */
	protected Date startDate;

	protected Integer pieces;
	
	protected Integer turnover;
	
	protected Integer profit;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="key asc"))
	private List<YearSizeStatistic> sizeStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="key asc"))
	private List<YearCategoryStatistic> categoryStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="key asc"))
	private List<YearPromoStatistic> promoStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="key asc"))
	private List<YearTopStatistic> topStatistics;

	public YearSellStatistic() {
		super();
		initLists();
	}
	
	public YearSellStatistic(Date startDate) {
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
	public List<YearSizeStatistic> getSizeStatistics() {
		return sizeStatistics;
	}

	public void setSizeStatistics(List<YearSizeStatistic> sizeStatistics) {
		this.sizeStatistics = sizeStatistics;
	}

	public List<YearCategoryStatistic> getCategoryStatistics() {
		return categoryStatistics;
	}

	public void setCategoryStatistics(List<YearCategoryStatistic> categoryStatistics) {
		this.categoryStatistics = categoryStatistics;
	}

	public List<YearPromoStatistic> getPromoStatistics() {
		return promoStatistics;
	}

	public void setPromoStatistics(List<YearPromoStatistic> promoStatistics) {
		this.promoStatistics = promoStatistics;
	}

	public List<YearTopStatistic> getTopStatistics() {
		return topStatistics;
	}

	public void setTopStatistics(List<YearTopStatistic> topStatistics) {
		this.topStatistics = topStatistics;
	}

	public YearSellStatisticDTO createDTO() {
		YearSellStatisticDTO dto = new YearSellStatisticDTO();
		fillDTO(dto);
		return dto;
	}

	@Override
	protected void dayToPeriodStart(GregorianCalendar gc) {
		gc.set(Calendar.MONTH, Calendar.JANUARY);
		gc.set(Calendar.DAY_OF_MONTH, 1);
	}
	
	@Override
	protected void addPeriod(GregorianCalendar gc) {
		gc.add(Calendar.YEAR, 1);
	}
	
	private void initLists() {
		setSizeStatistics(new ArrayList<YearSizeStatistic>());
		setCategoryStatistics(new ArrayList<YearCategoryStatistic>());
		setPromoStatistics(new ArrayList<YearPromoStatistic>());
		setTopStatistics(new ArrayList<YearTopStatistic>());
	}

}
