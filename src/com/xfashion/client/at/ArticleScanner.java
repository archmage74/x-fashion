package com.xfashion.client.at;

import com.xfashion.shared.BarcodeHelper;

public abstract class ArticleScanner {
	public void scan(String scannedText) {
		
		if (scannedText != null && scannedText.length() == 13) {
			for (Character c : scannedText.toCharArray()) {
				if (c < '0' || c > '9') {
					return;
				}
			}
			if (scannedText.toCharArray()[0] == BarcodeHelper.ARTICLE_PREFIX_CHAR) {
				String idString = scannedText.substring(0, 12);
				Long productNumber = Long.parseLong(idString);
				onSuccess(productNumber);
			} else {
				onError(scannedText);
			}
		}
	}
	
	public abstract void onSuccess(long ean);

	public abstract void onError(String scannedText);
	
}
