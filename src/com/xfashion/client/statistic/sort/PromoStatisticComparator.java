package com.xfashion.client.statistic.sort;

import java.util.Comparator;

import com.xfashion.client.promo.PromoDistributor;
import com.xfashion.shared.PromoDTO;
import com.xfashion.shared.statistic.PromoStatisticDTO;

public class PromoStatisticComparator implements Comparator<PromoStatisticDTO> {

	PromoDistributor promoDistributor;
	
	public PromoStatisticComparator() {
		this.promoDistributor = PromoDistributor.getInstance(); 
	}
	
	@Override
	public int compare(PromoStatisticDTO o1, PromoStatisticDTO o2) {
		if (o1 == o2) {
			return 0;
		}
		if (o1 == null || o1.getPieces() == null) {
			return 1;
		}
		if (o2 == null || o2.getPieces() == null) {
			return -1;
		}
		int index1 = Integer.MIN_VALUE;
		int index2 = Integer.MIN_VALUE;
		int index = 0;
		for (PromoDTO promo : promoDistributor.getPromos()) {
			if (o1.getPromoKeyString().equals(promo.getKey())) {
				index1 = index;
			}
			if (o2.getPromoKeyString().equals(promo.getKey())) {
				index2 = index;
			}
			index++;
		}
		return index1 - index2;
	}
	
}
