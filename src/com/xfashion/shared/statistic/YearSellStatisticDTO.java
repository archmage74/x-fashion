package com.xfashion.shared.statistic;

import java.util.Calendar;


public class YearSellStatisticDTO extends SellStatisticDTO {

	@Override
	public int getPeriodType() {
		return Calendar.YEAR;
	}

}
