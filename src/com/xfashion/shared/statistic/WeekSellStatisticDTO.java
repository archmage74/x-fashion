package com.xfashion.shared.statistic;

import java.util.Calendar;


public class WeekSellStatisticDTO extends SellStatisticDTO {

	@Override
	public int getPeriodType() {
		return Calendar.WEEK_OF_YEAR;
	}

}
