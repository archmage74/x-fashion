package com.xfashion.client.statistic.sort;

import java.util.Comparator;

import com.xfashion.client.at.category.CategoryDataProvider;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.statistic.CategoryStatisticDTO;

public class CategoryStatisticComparator implements Comparator<CategoryStatisticDTO> {

	CategoryDataProvider categoryProvider; 
	
	public CategoryStatisticComparator(CategoryDataProvider CategoryProvider) {
		this.categoryProvider = CategoryProvider;
	}
	
	@Override
	public int compare(CategoryStatisticDTO o1, CategoryStatisticDTO o2) {
		if (o1 == o2) {
			return 0;
		}
		if (o1 == null || o1.getCategory() == null) {
			return 1;
		}
		if (o2 == null || o2.getCategory() == null) {
			return -1;
		}
		int index1 = Integer.MIN_VALUE;
		int index2 = Integer.MIN_VALUE;
		int index = 0;
		for (CategoryDTO Category : categoryProvider.getAllItems()) {
			if (o1.getCategory().equals(Category.getName())) {
				index1 = index;
			}
			if (o2.getCategory().equals(Category.getName())) {
				index2 = index;
			}
			index++;
		}
		return index1 - index2;
	}
	
}
