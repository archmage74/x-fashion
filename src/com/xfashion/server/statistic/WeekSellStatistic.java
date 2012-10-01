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
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.statistic.WeekSellStatisticDTO;

@PersistenceCapable
public class WeekSellStatistic extends SellStatistic<WeekSizeStatistic, WeekCategoryStatistic, WeekPromoStatistic, WeekTopStatistic> {

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
	private List<WeekSizeStatistic> sizeStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="key asc"))
	private List<WeekCategoryStatistic> categoryStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="key asc"))
	private List<WeekPromoStatistic> promoStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="key asc"))
	private List<WeekTopStatistic> topStatistics;

	public WeekSellStatistic() {
		super();
		initLists();
	}
	
	public WeekSellStatistic(Date startDate) {
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
	public List<WeekSizeStatistic> getSizeStatistics() {
		return sizeStatistics;
	}

	public void setSizeStatistics(List<WeekSizeStatistic> sizeStatistics) {
		this.sizeStatistics = sizeStatistics;
	}

	public List<WeekCategoryStatistic> getCategoryStatistics() {
		return categoryStatistics;
	}

	public void setCategoryStatistics(List<WeekCategoryStatistic> categoryStatistics) {
		this.categoryStatistics = categoryStatistics;
	}

	public List<WeekPromoStatistic> getPromoStatistics() {
		return promoStatistics;
	}

	public void setPromoStatistics(List<WeekPromoStatistic> promoStatistics) {
		this.promoStatistics = promoStatistics;
	}

	public List<WeekTopStatistic> getTopStatistics() {
		return topStatistics;
	}

	public void setTopStatistics(List<WeekTopStatistic> topStatistics) {
		this.topStatistics = topStatistics;
	}

	public void add(SoldArticleDTO soldArticleDTO) {
		super.add(soldArticleDTO);
		addToDetailList(sizeStatistics, soldArticleDTO, WeekSizeStatistic.class);
		addToDetailList(categoryStatistics, soldArticleDTO, WeekCategoryStatistic.class);
		addToDetailList(promoStatistics, soldArticleDTO, WeekPromoStatistic.class);
		addToDetailList(topStatistics, soldArticleDTO, WeekTopStatistic.class);
	}

	@Override
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

	private void initLists() {
		setSizeStatistics(new ArrayList<WeekSizeStatistic>());
		setCategoryStatistics(new ArrayList<WeekCategoryStatistic>());
		setPromoStatistics(new ArrayList<WeekPromoStatistic>());
		setTopStatistics(new ArrayList<WeekTopStatistic>());
	}

}
