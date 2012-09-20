package com.xfashion.client.statistic.sort;

import java.util.Comparator;

import com.xfashion.client.at.size.SizeDataProvider;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.statistic.SizeStatisticDTO;

public class SizeStatisticComparator implements Comparator<SizeStatisticDTO> {

	SizeDataProvider sizeProvider; 
	
	public SizeStatisticComparator(SizeDataProvider sizeProvider) {
		this.sizeProvider = sizeProvider;
	}
	
	@Override
	public int compare(SizeStatisticDTO o1, SizeStatisticDTO o2) {
		if (o1 == o2) {
			return 0;
		}
		if (o1 == null || o1.getSize() == null) {
			return 1;
		}
		if (o2 == null || o2.getSize() == null) {
			return -1;
		}
		int index1 = Integer.MIN_VALUE;
		int index2 = Integer.MIN_VALUE;
		int index = 0;
		for (SizeDTO size : sizeProvider.getAllItems()) {
			if (o1.getSize().equals(size.getName())) {
				index1 = index;
			}
			if (o2.getSize().equals(size.getName())) {
				index2 = index;
			}
			index++;
		}
		return index1 - index2;
	}
	
}
