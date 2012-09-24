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

	List<WeekSellStatisticDTO> readCommonWeekSellStatistic(int i, int j);
	
	List<MonthSellStatisticDTO> readCommonMonthSellStatistic(int i, int j);
	
	List<YearSellStatisticDTO> readCommonYearSellStatistic(int i, int j);

	List<SizeStatisticDTO> readSizeStatistic(SellStatisticDTO sellStatistic);
	
	List<CategoryStatisticDTO> readCategoryStatistic(SellStatisticDTO sellStatistic);
	
	List<PromoStatisticDTO> readPromoStatistic(SellStatisticDTO sellStatistic);
	
	List<TopStatisticDTO> readTopStatistic(SellStatisticDTO sellStatistic);
	
	void writeStatistic(SoldArticleDTO soldArticle);

	void rewriteStatistic();

	SoldArticleDTO readSoldArticle(String soldArticleKey);

	List<SoldArticleDTO> readSoldArticles(int from, int to) throws IllegalArgumentException;
	
	List<SoldArticleDTO> readSoldArticlesOfShop(String shopKey, int from, int to) throws IllegalArgumentException;

	List<SoldArticleDTO> readOwnSoldArticles(int from, int to) throws IllegalArgumentException;
	
}
