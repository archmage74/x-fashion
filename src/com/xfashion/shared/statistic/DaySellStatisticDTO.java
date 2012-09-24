package com.xfashion.shared.statistic;

import java.util.Calendar;


public class DaySellStatisticDTO extends SellStatisticDTO {

	@Override
	public int getPeriodType() {
		return Calendar.DATE;
	}
	
}
