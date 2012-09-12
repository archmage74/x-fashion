package com.xfashion.client.stat;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.statistic.DaySellStatisticDTO;

public interface StatisticServiceAsync {

	void readCommonDaySellStatistic(int from, int to, AsyncCallback<List<DaySellStatisticDTO>> callback);

	void writeStatistic(SoldArticleDTO soldArticle, AsyncCallback<Void> callback);

	void rewriteStatistic(AsyncCallback<Void> callback);


}
