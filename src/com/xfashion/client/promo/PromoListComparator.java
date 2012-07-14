package com.xfashion.client.promo;

import java.util.Comparator;

import com.xfashion.shared.PromoDTO;

public class PromoListComparator implements Comparator<PromoDTO> {

	@Override
	public int compare(PromoDTO o1, PromoDTO o2) {
		if (o1.getPrice() == null) {
			if (o2.getPrice() == null) {
				if (o1.getPercent() == null) {
					return -1;
				} else {
					if (o2.getPercent() == null) {
						return 1;
					} else {
						return o1.getPercent().compareTo(o2.getPercent()); 
					}
				}
			} else {
				return 1;
			}
		} else {
			if (o2.getPrice() == null) {
				return -1;
			} else {
				return o1.getPrice().compareTo(o2.getPrice());
			}
		}
	}
	
}
