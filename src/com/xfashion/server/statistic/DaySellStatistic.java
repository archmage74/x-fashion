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
import com.xfashion.shared.DaySellStatisticDTO;
import com.xfashion.shared.SoldArticleDTO;

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
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "pieces desc"))
	private List<DaySizeStatistic> sizeStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "pieces desc"))
	private List<CategoryStatistic> categoryStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "pieces desc"))
	private List<PromoStatistic> promoStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "pieces desc"))
	private List<TopStatistic> topStatistics;

	public DaySellStatistic() {
		super();
		setDaySizeStatistics(new ArrayList<DaySizeStatistic>());
		setCategoryStatistics(new ArrayList<CategoryStatistic>());
		setPromoStatistics(new ArrayList<PromoStatistic>());
		setTopStatistics(new ArrayList<TopStatistic>());
	}

	public DaySellStatistic(Date startDate) {
		super(startDate);
		setDaySizeStatistics(new ArrayList<DaySizeStatistic>());
		setCategoryStatistics(new ArrayList<CategoryStatistic>());
		setPromoStatistics(new ArrayList<PromoStatistic>());
		setTopStatistics(new ArrayList<TopStatistic>());
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

	public List<DaySizeStatistic> getDaySizeStatistics() {
		return sizeStatistics;
	}

	public void setDaySizeStatistics(List<DaySizeStatistic> sizeStatistics) {
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

	public void add(SoldArticleDTO soldArticle) {
		super.add(soldArticle);
		addSizeStatistic(soldArticle);
	}

	private void addSizeStatistic(SoldArticleDTO soldArticle) {
		System.out.print("addSizeStatistic() called");
		DaySizeStatistic sizeStatistic = null;
		for (DaySizeStatistic s : getDaySizeStatistics()) {
			if (soldArticle.getSize().equals(s.getSize())) {
				sizeStatistic = s;
				break;
			}
		}
		if (sizeStatistic == null) {
			sizeStatistic = new DaySizeStatistic(soldArticle.getSize());
			getDaySizeStatistics().add(sizeStatistic);
		}
		sizeStatistic.setPieces(sizeStatistic.getPieces() + soldArticle.getAmount());
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

}
