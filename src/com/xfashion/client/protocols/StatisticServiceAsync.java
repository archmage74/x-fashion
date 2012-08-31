package com.xfashion.client.protocols;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.UserDTO;

public interface StatisticServiceAsync {

	void writeStatistic(UserDTO user, SoldArticleDTO soldArticle, AsyncCallback<Void> callback);

}
