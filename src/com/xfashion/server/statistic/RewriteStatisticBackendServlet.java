package com.xfashion.server.statistic;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xfashion.server.task.DistributePriceChangeServlet;
import com.xfashion.server.task.UpdateSellStatisticServlet;
import com.xfashion.shared.SoldArticleDTO;

public class RewriteStatisticBackendServlet extends HttpServlet {

	private static final long serialVersionUID = -4310701256638886115L;
	private static final Logger log = Logger.getLogger(DistributePriceChangeServlet.class.getName());
	private static final String sourceClass = UpdateSellStatisticServlet.class.getName();

//	private UserServiceImpl userService = new UserServiceImpl();
	private StatisticServiceImpl statisticService = new StatisticServiceImpl();
	
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
	
	public void doServe(HttpServletRequest request, HttpServletResponse response) {
		int batch = 0;
		int batchSize = 30;
		while (true) {
			List<SoldArticleDTO> soldArticles = statisticService.readSoldArticles(batch * batchSize, batch * batchSize + batchSize);
			if (soldArticles == null || soldArticles.size() == 0) {
				break;
			}
			for (SoldArticleDTO soldArticle : soldArticles) {
				writeStatistic(soldArticle);
			}
			batch ++;
		}
	}

	private void writeStatistic(SoldArticleDTO soldArticle) {
		boolean success = false;
		while (!success) {
			try {
				statisticService.writeStatistic(soldArticle);
				success = true;
			} catch (Exception e) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e1) {
					
				}
			}
		}
	}
	
}
