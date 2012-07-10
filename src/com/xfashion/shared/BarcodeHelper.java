package com.xfashion.shared;



public class BarcodeHelper {

	public static final Long ARTICLE_PREFIX = 1L;
	public static final Character ARTICLE_PREFIX_CHAR = '1';
	public static final Long DELIVERY_NOTICE_PREFIX = 2L;
	public static final Character DELIVERY_NOTICE_PREFIX_CHAR = '2';
	public static final Long PROMO_NOTICE_PREFIX = 3L;
	public static final Character PROMO_NOTICE_PREFIX_CHAR = '3';
	
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

	public String generateDeliveryNoticeEan(long deliveryNoticeId) {
		if (deliveryNoticeId > 299999999999L || deliveryNoticeId < 200000000000L) {
			throw new EanFormatException("delivery-notice-number out of range");
		}

		return generateEan(deliveryNoticeId);
	}

	public String generatePromoEan(Long promoEan) {
		if (promoEan > 399999999999L || promoEan < 300000000000L) {
			throw new EanFormatException("promo-ean out of range");
		}

		return generateEan(promoEan);
	}

}
