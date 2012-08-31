package com.xfashion.server.statistic;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.protocols.StatisticService;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.UserDTO;

public class StatisticServiceImpl extends RemoteServiceServlet implements StatisticService {

	private static final long serialVersionUID = 1L;

	@Override
	public void writeStatistic(UserDTO user, SoldArticleDTO soldArticle) {
		// TODO Auto-generated method stub
		
	}
	
}
