package com.xfashion.client.stat;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.DaySellStatisticDTO;
import com.xfashion.shared.SoldArticleDTO;

@RemoteServiceRelativePath("statisticService")
public interface StatisticService extends RemoteService {

	List<DaySellStatisticDTO> readCommonDaySellStatistic(int from, int to);

	void writeStatistic(SoldArticleDTO soldArticle);

	
}
