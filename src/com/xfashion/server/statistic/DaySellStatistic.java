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
import com.xfashion.shared.statistic.DaySellStatisticDTO;

@PersistenceCapable
public class DaySellStatistic extends SellStatistic {

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
	private List<DaySizeStatistic> sizeStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "key asc"))
	private List<DayCategoryStatistic> categoryStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "key asc"))
	private List<DayPromoStatistic> promoStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "key asc"))
	private List<DayTopStatistic> topStatistics;

	public DaySellStatistic() {
		super();
		initLists();
	}

	public DaySellStatistic(Date startDate) {
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

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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

	public List<DaySizeStatistic> getDaySizeStatistics() {
		return sizeStatistics;
	}

	public void setSizeStatistics(List<DaySizeStatistic> sizeStatistics) {
		this.sizeStatistics = sizeStatistics;
	}

	public List<DayCategoryStatistic> getCategoryStatistics() {
		return categoryStatistics;
	}

	public void setCategoryStatistics(List<DayCategoryStatistic> categoryStatistics) {
		this.categoryStatistics = categoryStatistics;
	}

	public List<DayPromoStatistic> getPromoStatistics() {
		return promoStatistics;
	}

	public void setPromoStatistics(List<DayPromoStatistic> promoStatistics) {
		this.promoStatistics = promoStatistics;
	}

	public List<DayTopStatistic> getTopStatistics() {
		return topStatistics;
	}

	public void setTopStatistics(List<DayTopStatistic> topStatistics) {
		this.topStatistics = topStatistics;
	}

	public void add(SoldArticleDTO soldArticleDTO) {
//		System.out.println("DaySellStatistic.add() called for soldArticle.name=" + soldArticleDTO.getArticleName());
		super.add(soldArticleDTO);
		addToDetailList(sizeStatistics, soldArticleDTO, DaySizeStatistic.class);
		addToDetailList(categoryStatistics, soldArticleDTO, DayCategoryStatistic.class);
		addToDetailList(promoStatistics, soldArticleDTO, DayPromoStatistic.class);
		addToDetailList(topStatistics, soldArticleDTO, DayTopStatistic.class);
	}

	public DaySellStatisticDTO createDTO() {
		DaySellStatisticDTO dto = new DaySellStatisticDTO();
		fillDTO(dto);
		return dto;
	}

	@Override
	protected void dayToPeriodStart(GregorianCalendar gc) {

	}

	@Override
	protected void addPeriod(GregorianCalendar gc) {
		gc.add(Calendar.DAY_OF_MONTH, 1);
	}
	
	private void initLists() {
		setSizeStatistics(new ArrayList<DaySizeStatistic>());
		setCategoryStatistics(new ArrayList<DayCategoryStatistic>());
		setPromoStatistics(new ArrayList<DayPromoStatistic>());
		setTopStatistics(new ArrayList<DayTopStatistic>());
	}

}
