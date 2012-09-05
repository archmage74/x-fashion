package com.xfashion.client.protocols;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.SoldArticleDTO;

@RemoteServiceRelativePath("statisticService")
public interface StatisticService extends RemoteService {

	public void writeStatistic(SoldArticleDTO soldArticle);
	
}
