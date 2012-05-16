package com.xfashion.pdf;

import com.xfashion.shared.ArticleTypeDTO;

public class BarcodeHelper {

	public static final long MULT_TYPE = 100000000000L;
	public static final long MULT_CAT = 1000000000L;
	public static final long MULT_BUYP = 1000000L;
	public static final long MULT_PROD = 1L;

	public String generateArticleEan(ArticleTypeDTO at) {
		if (at.getBuyPrice() > 99999) {
			throw new RuntimeException("buy price too high");
		}
		if (at.getProductNumber() > 999999) {
			throw new RuntimeException("product number too long");
		}
		long type = 1L * MULT_TYPE;
		long categoryId = at.getCategoryId() * MULT_CAT;
		long buyPrice = (at.getBuyPrice() / 100) * MULT_BUYP;
		long productNumber = at.getProductNumber() * MULT_PROD;

		long val = type + categoryId + buyPrice + productNumber;

		return generateEan(val);
	}
	
	public String generateEan(long value) {
		long checksum = calculateChecksum(value);
		return String.format("%12d%1d", value, checksum);
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
		long checksum = 10 - (sum % 10);
		return checksum;
	}

}
