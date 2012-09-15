package com.xfashion.server.statistic;

import java.util.List;

import com.xfashion.shared.SoldArticleDTO;

public class StatisticAdder {

	public <T extends SellStatistic<?,?,?,?>> void addToStatistic(Class<T> clazz, List<T> dtos, SoldArticleDTO soldArticle) {
		T dto = null;
		int index = 0;
		if (dtos.size() != 0) {
			while (dto == null && index < dtos.size()) {
				T search = dtos.get(index);
				if (search.isWithinPeriod(soldArticle.getSellDate())) {
					dto = search;
				} else {
					if (search.getStartDate().getTime() < soldArticle.getSellDate().getTime()) {
						break;
					}
					index++;
				}
			}
		}
		if (dto == null) {
			try {
				dto = clazz.newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			dto.init(soldArticle.getSellDate());
			dtos.add(index, dto);
		}
		dto.add(soldArticle);
	}

}
