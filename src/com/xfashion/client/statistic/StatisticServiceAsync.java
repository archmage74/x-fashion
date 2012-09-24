package com.xfashion.client.statistic;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.statistic.CategoryStatisticDTO;
import com.xfashion.shared.statistic.DaySellStatisticDTO;
import com.xfashion.shared.statistic.MonthSellStatisticDTO;
import com.xfashion.shared.statistic.PromoStatisticDTO;
import com.xfashion.shared.statistic.SellStatisticDTO;
import com.xfashion.shared.statistic.SizeStatisticDTO;
import com.xfashion.shared.statistic.TopStatisticDTO;
import com.xfashion.shared.statistic.WeekSellStatisticDTO;
import com.xfashion.shared.statistic.YearSellStatisticDTO;

public interface StatisticServiceAsync {

	void readCommonDaySellStatistic(int from, int to, AsyncCallback<List<DaySellStatisticDTO>> callback);

	void readCommonWeekSellStatistic(int i, int j, AsyncCallback<List<WeekSellStatisticDTO>> callback);

	void readCommonMonthSellStatistic(int i, int j, AsyncCallback<List<MonthSellStatisticDTO>> callback);

	void readCommonYearSellStatistic(int i, int j, AsyncCallback<List<YearSellStatisticDTO>> callback);

	void readSizeStatistic(SellStatisticDTO sellStatistic, AsyncCallback<List<SizeStatisticDTO>> callback);

	void readCategoryStatistic(SellStatisticDTO sellStatistic, AsyncCallback<List<CategoryStatisticDTO>> callback);

	void readPromoStatistic(SellStatisticDTO sellStatistic, AsyncCallback<List<PromoStatisticDTO>> callback);

	void readTopStatistic(SellStatisticDTO sellStatistic, AsyncCallback<List<TopStatisticDTO>> callback);

	void writeStatistic(SoldArticleDTO soldArticle, AsyncCallback<Void> callback);

	void rewriteStatistic(AsyncCallback<Void> callback);

	void readSoldArticle(String soldArticleKey, AsyncCallback<SoldArticleDTO> callback);
	
	void readSoldArticles(int from, int to, AsyncCallback<List<SoldArticleDTO>> callback);

	void readSoldArticles(SellStatisticDTO sellStatistic, int fromIndex, int toIndex, AsyncCallback<List<SoldArticleDTO>> callback);
	
	void readSoldArticlesOfShop(String shopKey, int from, int to, AsyncCallback<List<SoldArticleDTO>> callback);

	void readOwnSoldArticles(int from, int to, AsyncCallback<List<SoldArticleDTO>> callback);

	
}
