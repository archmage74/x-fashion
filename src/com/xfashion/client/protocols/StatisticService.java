package com.xfashion.client.protocols;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.UserDTO;

@RemoteServiceRelativePath("statisticService")
public interface StatisticService extends RemoteService {

	public void writeStatistic(UserDTO user, SoldArticleDTO soldArticle);
	
}
