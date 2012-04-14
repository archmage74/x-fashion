package com.xfashion.client;

import com.google.gwt.i18n.client.NumberFormat;

public class Formatter {

	private static Formatter formatter;
	
	private NumberFormat currencyFormat;
	
	public Formatter() {
		currencyFormat = NumberFormat.getCurrencyFormat("EUR");
	}
	
	public static Formatter getInstance() {
		if (formatter == null) {
			formatter = new Formatter();
		}
		return formatter;
	}
	
	public String formatCents(Integer cents) {
		return currencyFormat.format(cents.doubleValue() / 100);
	}
	
	public Integer parseEurToCents(String eur) {
		return new Double(currencyFormat.parse(eur) * 100).intValue();
	}
	
	public String formatProductNumber(Long productNumber) {
		return "" + productNumber;
	}
	
}
