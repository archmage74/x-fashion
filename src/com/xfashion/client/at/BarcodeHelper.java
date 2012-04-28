package com.xfashion.client.at;

import com.google.gwt.core.client.GWT;
import com.xfashion.shared.ArticleTypeDTO;

public class BarcodeHelper {

	private BarcodeMessages barcodeMessages = GWT.create(BarcodeMessages.class);
	
	public String generateEan(ArticleTypeDTO at) {
		int type = 1;
		long categoryId = at.getCategoryId();
		int buyPrice = at.getBuyPrice() / 100;
		long productNumber = at.getProductNumber();
		long parity = calculateParity(at.getProductNumber());
		if (buyPrice > 999) {
			throw new RuntimeException("buy price too high");
		}
		if (productNumber > 999999) {
			throw new RuntimeException("product number too long");
		}
		
		String ean = barcodeMessages.ean(type, categoryId, buyPrice, productNumber, parity);
		return ean;
	}

	public long calculateParity(long value) {
		long sum = 0;
		sum += value / 100000;
		sum += (value % 100000) / 10000;
		sum += (value % 10000) / 1000;
		sum += (value % 1000) / 100;
		sum += (value % 100) / 10;
		sum += (value % 10);
		return sum % 10;
	}

}
