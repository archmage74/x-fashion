package com.xfashion.client.statistic.sort;

import java.util.Comparator;

import com.xfashion.shared.statistic.TopStatisticDTO;

public class TopStatisticComparator implements Comparator<TopStatisticDTO> {

	public TopStatisticComparator() {
	}
	
	@Override
	public int compare(TopStatisticDTO o1, TopStatisticDTO o2) {
		if (o1 == o2) {
			return 0;
		}
		if (o1 == null || o1.getPieces() == null) {
			return 1;
		}
		if (o2 == null || o2.getPieces() == null) {
			return -1;
		}
		return o2.getPieces() - o1.getPieces();
	}
	
}
