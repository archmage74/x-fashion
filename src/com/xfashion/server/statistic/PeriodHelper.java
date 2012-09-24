package com.xfashion.server.statistic;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class PeriodHelper {

	public DateRange getDateRange(Date date, int periodType) {
		SellStatistic<?, ?, ?, ?> period = createPeriod(date, periodType);
		
		Date start = period.getStartDate();
		Calendar calendar = createCalendar();
		calendar.setTime(start);
		calendar.add(periodType, 1);
		Date end = calendar.getTime();
		
		return new DateRange(start, end);
	}

	private SellStatistic<?, ?, ?, ?> createPeriod(Date date, int periodType) {
		SellStatistic<?, ?, ?, ?> period = null;
		switch (periodType) {
		case Calendar.DATE:
			period = new DaySellStatistic(date);
			break;
		case Calendar.WEEK_OF_YEAR:
			period = new WeekSellStatistic(date);
			break;
		case Calendar.MONTH:
			period = new MonthSellStatistic(date);
			break;
		case Calendar.YEAR:
			period = new YearSellStatistic(date);
			break;
		}
		return period;
	}

	public GregorianCalendar createCalendar() {
		GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("Europe/Vienna"));
		gc.setFirstDayOfWeek(Calendar.MONDAY);
		return gc;
	}

}
