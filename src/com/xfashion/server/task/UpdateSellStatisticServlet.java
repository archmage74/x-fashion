package com.xfashion.server.task;

import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xfashion.client.statistic.StatisticService;
import com.xfashion.client.user.UserService;
import com.xfashion.server.statistic.StatisticServiceImpl;
import com.xfashion.server.user.UserServiceImpl;
import com.xfashion.shared.SoldArticleDTO;

public class UpdateSellStatisticServlet extends HttpServlet {

	private static final long serialVersionUID = -415839090605699252L;
	private static final Logger log = Logger.getLogger(DistributePriceChangeServlet.class.getName());
	private static final String sourceClass = UpdateSellStatisticServlet.class.getName();

	public static final String PARAM_SOLD_ARTICLE_KEY = "soldArticleKey";
	
	protected UserService userService;
	protected StatisticService statisticService;
	
	@Override
	public void init() {
		userService = new UserServiceImpl();
		statisticService = new StatisticServiceImpl();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		log.entering(sourceClass, "doGet");
		doServe(request, response);
		log.exiting(sourceClass, "doGet");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		log.entering(sourceClass, "doPost");
		doServe(request, response);
		log.exiting(sourceClass, "doPost");
	}
	
	protected void doServe(HttpServletRequest request, HttpServletResponse response) {
		log.entering(sourceClass, "doServe");

		String soldArticleKey = request.getParameter(PARAM_SOLD_ARTICLE_KEY);

		SoldArticleDTO soldArticle = statisticService.readSoldArticle(soldArticleKey);
		
		statisticService.writeStatistic(soldArticle);
		
		log.exiting(sourceClass, "doServe");
	}

}
