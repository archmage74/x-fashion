package com.xfashion.server.statistic;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.protocols.StatisticService;
import com.xfashion.server.PMF;
import com.xfashion.shared.SoldArticleDTO;

public class StatisticServiceImpl extends RemoteServiceServlet implements StatisticService {

	private static final long serialVersionUID = 1L;

	private StatisticAdder adder = new StatisticAdder();
	
	@Override
	public void writeStatistic(SoldArticleDTO soldArticle) {
		writeCommonStatistic(soldArticle);
		writeUserStatistic(soldArticle);
	}

	private void writeCommonStatistic(SoldArticleDTO soldArticle) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			DaySellStatistics statistics = readCommonDaySellStatistics(pm);
			adder.addToDayStatistic(statistics.getDaySellStatistics(), soldArticle);
		} finally {
			pm.close();
		}
	}

	private void writeUserStatistic(SoldArticleDTO soldArticle) {

	}

	public List<DaySellStatistic> readCommonDaySellStatistics() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<DaySellStatistic> dtos;
		try {
			DaySellStatistics items = readCommonDaySellStatistics(pm);
			dtos = items.getDaySellStatistics();
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private DaySellStatistics readCommonDaySellStatistics(PersistenceManager pm) {
		Query query = pm.newQuery(DaySellStatistics.class);
		DaySellStatistics item;
		List<DaySellStatistics> items = (List<DaySellStatistics>) query.execute();
		if (items.size() == 0) {
			item = new DaySellStatistics();
			item = pm.makePersistent(item);
		} else {
			item = items.get(0);
		}
		return item;
	}

	@SuppressWarnings("unchecked")
	public void deleteAllDayStatistics() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(DaySellStatistic.class);
			query.setRange(0, 20);
			Collection<DaySellStatistics> items = (Collection<DaySellStatistics>) query.execute();
			while (items.size() > 0) {
				pm.deletePersistentAll(items);
				items = (Collection<DaySellStatistics>) query.execute();
			}
		} finally {
			pm.close();
		}
	}
	
}
