package com.xfashion.shared;


public class BarcodeHelper {

	public String generateArticleEan(ArticleTypeDTO at) {
		if (at.getProductNumber() > 199999999999L || at.getProductNumber() < 100000000000L) {
			throw new RuntimeException("product number out of range");
		}
		long val = at.getProductNumber();

		return generateEan(val);
	}
	
	public String generateEan(long value) {
		long checksum = calculateChecksum(value);
		return "" + value + checksum;
	}

	public long calculateChecksum(long value) {
		long mult = 3;
		long sum = 0;
		while (value > 0) {
			sum += mult * (value % 10);
			if (mult == 3) {
				mult = 1;
			} else {
				mult = 3;
			}
			value = value / 10;
		}
		long checksum = (10 - (sum % 10)) % 10;
		return checksum;
	}

}
