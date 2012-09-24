package com.xfashion.shared.statistic;

import java.util.Calendar;


public class MonthSellStatisticDTO extends SellStatisticDTO {

	@Override
	public int getPeriodType() {
		return Calendar.MONTH;
	}

}
