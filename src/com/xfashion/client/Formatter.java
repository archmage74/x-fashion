package com.xfashion.client;

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
	
	public String formatCentsToCurrency(Integer cents) {
		return currencyFormat.format(cents.doubleValue() / 100);
	}
	
	public String formatCentsToCurrencyOrUnknown(Integer cents) {
		if (cents != null) {
			return formatter.formatCentsToCurrency(cents);
		} else {
			return textMessages.unknownPrice();
		}
	}
	
	public String formatCentsToValue(Integer cents) {
		return currencyValueFormat.format(cents.doubleValue() / 100);
	}
	
	public Integer parseEurToCents(String eur) {
		return new Integer((int) Math.round(currencyValueFormat.parse(eur) * 100.0));
	}
	
	public String formatProductNumber(Long productNumber) {
		return "" + productNumber;
	}
	
}
