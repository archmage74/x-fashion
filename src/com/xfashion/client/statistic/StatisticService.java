package com.xfashion.client.statistic;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.statistic.DaySellStatisticDTO;
import com.xfashion.shared.statistic.MonthSellStatisticDTO;
import com.xfashion.shared.statistic.WeekSellStatisticDTO;
import com.xfashion.shared.statistic.YearSellStatisticDTO;

@RemoteServiceRelativePath("statisticService")
public interface StatisticService extends RemoteService {

	List<DaySellStatisticDTO> readCommonDaySellStatistic(int from, int to);

	List<WeekSellStatisticDTO> readCommonWeekSellStatistic(int i, int j);
	
	List<MonthSellStatisticDTO> readCommonMonthSellStatistic(int i, int j);
	
	List<YearSellStatisticDTO> readCommonYearSellStatistic(int i, int j);

	void writeStatistic(SoldArticleDTO soldArticle);

	void rewriteStatistic();

}
