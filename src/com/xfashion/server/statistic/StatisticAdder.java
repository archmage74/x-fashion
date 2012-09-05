package com.xfashion.server.statistic;

import java.util.List;

import com.xfashion.shared.SoldArticleDTO;

public class StatisticAdder {

	public void addToDayStatistic(List<DaySellStatistic> dtos, SoldArticleDTO soldArticle) {
		DaySellStatistic dto = null;
		int index = 0;
		if (dtos.size() != 0) {
			while (dto == null && index < dtos.size()) {
				DaySellStatistic search = dtos.get(index);
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
			dto = new DaySellStatistic();
			dtos.add(index, dto);
		}
		dto.add(soldArticle);
	}

}
