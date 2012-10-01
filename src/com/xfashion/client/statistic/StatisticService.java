package com.xfashion.client.statistic;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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

@RemoteServiceRelativePath("statisticService")
public interface StatisticService extends RemoteService {

	List<DaySellStatisticDTO> readCommonDaySellStatistic(int from, int to);

	List<WeekSellStatisticDTO> readCommonWeekSellStatistic(int from, int to);
	
	List<MonthSellStatisticDTO> readCommonMonthSellStatistic(int from, int to);
	
	List<YearSellStatisticDTO> readCommonYearSellStatistic(int from, int to);

	List<DaySellStatisticDTO> readShopDaySellStatistic(String shopKey, int from, int to);

	List<WeekSellStatisticDTO> readShopWeekSellStatistic(String shopKey, int from, int to);
	
	List<MonthSellStatisticDTO> readShopMonthSellStatistic(String shopKey, int from, int to);
	
	List<YearSellStatisticDTO> readShopYearSellStatistic(String shopKey, int from, int to);

	List<SizeStatisticDTO> readSizeStatistic(SellStatisticDTO sellStatistic);
	
	List<CategoryStatisticDTO> readCategoryStatistic(SellStatisticDTO sellStatistic);
	
	List<PromoStatisticDTO> readPromoStatistic(SellStatisticDTO sellStatistic);
	
	List<TopStatisticDTO> readTopStatistic(SellStatisticDTO sellStatistic);
	
	void writeStatistic(SoldArticleDTO soldArticle);

	void rewriteStatistic();

	SoldArticleDTO readSoldArticle(String soldArticleKey);

	List<SoldArticleDTO> readSoldArticles(int from, int to);

	List<SoldArticleDTO> readSoldArticles(SellStatisticDTO sellStatistic, int fromIndex, int toIndex);
	
	List<SoldArticleDTO> readSoldArticlesOfShop(String shopKey, int from, int to);

	List<SoldArticleDTO> readOwnSoldArticles(int from, int to);
	
}
