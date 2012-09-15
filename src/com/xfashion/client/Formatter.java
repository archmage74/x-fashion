package com.xfashion.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.xfashion.client.resources.TextMessages;

public class Formatter {

	private static Formatter formatter;

	protected TextMessages textMessages = GWT.create(TextMessages.class);

	private NumberFormat currencyFormat;
	private NumberFormat currencyValueFormat;

	protected Formatter() {
		currencyFormat = NumberFormat.getCurrencyFormat("EUR");
		currencyValueFormat = NumberFormat.getDecimalFormat();
	}

	public static Formatter getInstance() {
		if (formatter == null) {
			formatter = new Formatter();
		}
		return formatter;
	}

	public String centsToCurrency(Integer cents) {
		return currencyFormat.format(cents.doubleValue() / 100);
	}

	public String centsToCurrencyOrUnknown(Integer cents) {
		if (cents != null) {
			return formatter.centsToCurrency(cents);
		} else {
			return textMessages.unknownPrice();
		}
	}

	public String centsToValue(Integer cents) {
		return currencyValueFormat.format(cents.doubleValue() / 100);
	}

	public Integer parseEurToCents(String eur) {
		return new Integer((int) Math.round(currencyValueFormat.parse(eur) * 100.0));
	}

	public String productNumber(Long productNumber) {
		return "" + productNumber;
	}

	public String week(Date date) {
		return textMessages.week(date, calendarWeek(date));
	}
	
	@SuppressWarnings("deprecation")
	public Long calendarWeek(Date inputDate) {
		Date thisThursday = new Date(inputDate.getYear(), inputDate.getMonth(), inputDate.getDate() - weekday(inputDate) + 4);
		Date firstThursdayOfYear = new Date(thisThursday.getYear(), 0, 1);
		while (weekday(firstThursdayOfYear) != 4) {
			firstThursdayOfYear.setDate(firstThursdayOfYear.getDate() + 1);
		}
		Date firstMondayOfYear = new Date(firstThursdayOfYear.getYear(), 0, firstThursdayOfYear.getDate() - 3);
		Long cw = (thisThursday.getTime() - firstMondayOfYear.getTime()) / 1000 / 60 / 60 / 24 / 7 + 1;
		return cw;
	}

	@SuppressWarnings("deprecation")
	private Integer weekday(Date date) {
		int weekday = date.getDay();
		if (weekday == 0) {
			weekday = 7;
		}
		return weekday;
	}
	
}
