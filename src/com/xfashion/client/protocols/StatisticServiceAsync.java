package com.xfashion.client.protocols;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.SoldArticleDTO;

public interface StatisticServiceAsync {

	void writeStatistic(SoldArticleDTO soldArticle, AsyncCallback<Void> callback);

}
