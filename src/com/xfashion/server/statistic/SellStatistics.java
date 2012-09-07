package com.xfashion.server.statistic;

import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class SellStatistics {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="startDate desc"))
	private List<DaySellStatistic> daySellStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="startDate desc"))
	private List<WeekSellStatistic> weekSellStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="startDate desc"))
	private List<MonthSellStatistic> monthSellStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="startDate desc"))
	private List<YearSellStatistic> yearSellStatistics;

	public Key getKey() {
		return key;
	}

	public List<DaySellStatistic> getDaySellStatistics() {
		return daySellStatistics;
	}

	public void setDaySellStatistics(List<DaySellStatistic> daySellStatistics) {
		this.daySellStatistics = daySellStatistics;
	}

	public List<WeekSellStatistic> getWeekSellStatistics() {
		return weekSellStatistics;
	}

	public void setWeekSellStatistics(List<WeekSellStatistic> weekSellStatistics) {
		this.weekSellStatistics = weekSellStatistics;
	}

	public List<MonthSellStatistic> getMonthSellStatistics() {
		return monthSellStatistics;
	}

	public void setMonthSellStatistics(List<MonthSellStatistic> monthSellStatistics) {
		this.monthSellStatistics = monthSellStatistics;
	}

	public List<YearSellStatistic> getYearSellStatistics() {
		return yearSellStatistics;
	}

	public void setYearSellStatistics(List<YearSellStatistic> yearSellStatistics) {
		this.yearSellStatistics = yearSellStatistics;
	}
	
}
