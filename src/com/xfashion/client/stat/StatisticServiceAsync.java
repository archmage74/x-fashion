package com.xfashion.client.stat;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.DaySellStatisticDTO;
import com.xfashion.shared.SoldArticleDTO;

public interface StatisticServiceAsync {

	void readCommonDaySellStatistic(int from, int to, AsyncCallback<List<DaySellStatisticDTO>> callback);

	void writeStatistic(SoldArticleDTO soldArticle, AsyncCallback<Void> callback);


}
