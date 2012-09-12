package com.xfashion.server.statistic;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.backends.BackendService;
import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.stat.StatisticService;
import com.xfashion.server.PMF;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.statistic.DaySellStatisticDTO;

public class StatisticServiceImpl extends RemoteServiceServlet implements StatisticService {

	private static final long serialVersionUID = 1L;

	private StatisticAdder adder = new StatisticAdder();

	@Override
	public List<DaySellStatisticDTO> readCommonDaySellStatistic(int from, int to) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<DaySellStatisticDTO> dtos = new ArrayList<DaySellStatisticDTO>();
		try {
			SellStatistics sellStatistics = readCommonSellStatistics(pm);
			List<DaySellStatistic> stats = sellStatistics.getDaySellStatistics(); 
			for (int index = from; index < to && index < stats.size(); index++) {
				dtos.add(stats.get(index).createDTO());
			}
		} finally {
			pm.close();
		}
		return dtos;
	}
	
	@Override
	public void writeStatistic(SoldArticleDTO soldArticle) {
		writeCommonStatistic(soldArticle);
		writeUserStatistic(soldArticle);
	}

	private void writeCommonStatistic(SoldArticleDTO soldArticle) {
		writeCommonDaySellStatistic(soldArticle);
		writeCommonWeekSellStatistic(soldArticle);
		writeCommonMonthSellStatistic(soldArticle);
		writeCommonYearSellStatistic(soldArticle);
	}

	private void writeCommonDaySellStatistic(SoldArticleDTO soldArticle) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.setOptimistic(false);
			tx.begin();
			SellStatistics statistics = readCommonSellStatistics(pm);
			adder.addToStatistic(DaySellStatistic.class, statistics.getDaySellStatistics(), soldArticle);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	private void writeCommonWeekSellStatistic(SoldArticleDTO soldArticle) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			SellStatistics statistics = readCommonSellStatistics(pm);
			adder.addToStatistic(WeekSellStatistic.class, statistics.getWeekSellStatistics(), soldArticle);
		} finally {
			pm.close();
		}
	}

	private void writeCommonMonthSellStatistic(SoldArticleDTO soldArticle) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			SellStatistics statistics = readCommonSellStatistics(pm);
			adder.addToStatistic(MonthSellStatistic.class, statistics.getMonthSellStatistics(), soldArticle);
		} finally {
			pm.close();
		}
	}

	private void writeCommonYearSellStatistic(SoldArticleDTO soldArticle) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			SellStatistics statistics = readCommonSellStatistics(pm);
			adder.addToStatistic(YearSellStatistic.class, statistics.getYearSellStatistics(), soldArticle);
		} finally {
			pm.close();
		}
	}

	private void writeUserStatistic(SoldArticleDTO soldArticle) {

	}

	@SuppressWarnings("unchecked")
	private SellStatistics readCommonSellStatistics(PersistenceManager pm) {
		Query query = pm.newQuery(SellStatistics.class);
		SellStatistics item;
		List<SellStatistics> items = (List<SellStatistics>) query.execute();
		if (items.size() == 0) {
			item = new SellStatistics();
			item = pm.makePersistent(item);
		} else {
			item = items.get(0);
		}
		return item;
	}

	public void deleteAllCommonStatistics() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			SellStatistics sellStatistics = readCommonSellStatistics(pm);
			pm.deletePersistent(sellStatistics);
		} finally {
			pm.close();
		}
	}

	@Override
	public void rewriteStatistic() {
		BackendService backendService = BackendServiceFactory.getBackendService();
		String url = backendService.getBackendAddress("rewritestatistic");
		Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions options = TaskOptions.Builder.withUrl("/backend/rewritestatisticbackend")
			.header("Host", url);
			// .param(UpdateSellStatisticServlet.PARAM_SOLD_ARTICLE_KEY, soldArticle.getKeyAsString()));
		queue.add(options);
	}
}
