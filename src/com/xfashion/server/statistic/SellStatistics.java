package com.xfashion.server.statistic;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.xfashion.shared.statistic.DaySellStatisticDTO;
import com.xfashion.shared.statistic.MonthSellStatisticDTO;
import com.xfashion.shared.statistic.SellStatisticDTO;
import com.xfashion.shared.statistic.WeekSellStatisticDTO;
import com.xfashion.shared.statistic.YearSellStatisticDTO;

@PersistenceCapable
public class SellStatistics {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "startDate desc"))
	private List<DaySellStatistic> daySellStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "startDate desc"))
	private List<WeekSellStatistic> weekSellStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "startDate desc"))
	private List<MonthSellStatistic> monthSellStatistics;

	@Persistent
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "startDate desc"))
	private List<YearSellStatistic> yearSellStatistics;

	public SellStatistics() {
		this.daySellStatistics = new ArrayList<DaySellStatistic>();
		this.weekSellStatistics = new ArrayList<WeekSellStatistic>();
		this.monthSellStatistics = new ArrayList<MonthSellStatistic>();
		this.yearSellStatistics = new ArrayList<YearSellStatistic>();
	}
	
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

	public List<DaySellStatisticDTO> createDaySellStatisticDTO(int from, int to) {
		List<DaySellStatisticDTO> dtos = createSellStatisticDTOs(getDaySellStatistics(), DaySellStatisticDTO.class, from, to);
		return dtos;
	}

	public List<WeekSellStatisticDTO> createWeekSellStatisticDTO(int from, int to) {
		List<WeekSellStatisticDTO> dtos = createSellStatisticDTOs(getWeekSellStatistics(), WeekSellStatisticDTO.class, from, to);
		return dtos;
	}

	public List<MonthSellStatisticDTO> createMonthSellStatisticDTO(int from, int to) {
		List<MonthSellStatisticDTO> dtos = createSellStatisticDTOs(getMonthSellStatistics(), MonthSellStatisticDTO.class, from, to);
		return dtos;
	}

	public List<YearSellStatisticDTO> createYearSellStatisticDTO(int from, int to) {
		List<YearSellStatisticDTO> dtos = createSellStatisticDTOs(getYearSellStatistics(), YearSellStatisticDTO.class, from, to);
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private <S extends SellStatisticDTO, T extends SellStatistic<?, ?, ?, ?>> List<S> createSellStatisticDTOs(List<T> stats,
			Class<S> dtoClass, int from, int to) {
		List<S> dtos = new ArrayList<S>();
		for (int index = from; index < to && index < stats.size(); index++) {
			dtos.add((S) stats.get(index).createDTO());
		}
		return dtos;
	}

}
