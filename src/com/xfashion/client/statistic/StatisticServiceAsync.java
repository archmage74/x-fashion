package com.xfashion.client.statistic;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.statistic.DaySellStatisticDTO;
import com.xfashion.shared.statistic.MonthSellStatisticDTO;
import com.xfashion.shared.statistic.WeekSellStatisticDTO;
import com.xfashion.shared.statistic.YearSellStatisticDTO;

public interface StatisticServiceAsync {

	void readCommonDaySellStatistic(int from, int to, AsyncCallback<List<DaySellStatisticDTO>> callback);

	void readCommonWeekSellStatistic(int i, int j, AsyncCallback<List<WeekSellStatisticDTO>> callback);

	void readCommonMonthSellStatistic(int i, int j, AsyncCallback<List<MonthSellStatisticDTO>> callback);

	void readCommonYearSellStatistic(int i, int j, AsyncCallback<List<YearSellStatisticDTO>> callback);

	void writeStatistic(SoldArticleDTO soldArticle, AsyncCallback<Void> callback);

	void rewriteStatistic(AsyncCallback<Void> callback);

}
