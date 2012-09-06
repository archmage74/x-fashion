package com.xfashion.server.statistic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.stat.StatisticService;
import com.xfashion.server.PMF;
import com.xfashion.shared.DaySellStatisticDTO;
import com.xfashion.shared.SoldArticleDTO;

public class StatisticServiceImpl extends RemoteServiceServlet implements StatisticService {

	private static final long serialVersionUID = 1L;

	private StatisticAdder adder = new StatisticAdder();

	@Override
	public List<DaySellStatisticDTO> readCommonDaySellStatistic(int from, int to) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<DaySellStatisticDTO> dtos = new ArrayList<DaySellStatisticDTO>();
		try {
			DaySellStatistics container = readCommonDaySellStatistics(pm);
			List<DaySellStatistic> stats = container.getSellStatistics(); 
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
		try {
			DaySellStatistics statistics = readCommonDaySellStatistics(pm);
			adder.addToStatistic(DaySellStatistic.class, statistics.getSellStatistics(), soldArticle);
		} finally {
			pm.close();
		}
	}

	private void writeCommonWeekSellStatistic(SoldArticleDTO soldArticle) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			WeekSellStatistics statistics = readCommonWeekSellStatistics(pm);
			adder.addToStatistic(WeekSellStatistic.class, statistics.getSellStatistics(), soldArticle);
		} finally {
			pm.close();
		}
	}

	private void writeCommonMonthSellStatistic(SoldArticleDTO soldArticle) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			MonthSellStatistics statistics = readCommonMonthSellStatistics(pm);
			adder.addToStatistic(MonthSellStatistic.class, statistics.getSellStatistics(), soldArticle);
		} finally {
			pm.close();
		}
	}

	private void writeCommonYearSellStatistic(SoldArticleDTO soldArticle) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			YearSellStatistics statistics = readCommonYearSellStatistics(pm);
			adder.addToStatistic(YearSellStatistic.class, statistics.getSellStatistics(), soldArticle);
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
			dtos = items.getSellStatistics();
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

	public List<WeekSellStatistic> readCommonWeekSellStatistics() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<WeekSellStatistic> dtos;
		try {
			WeekSellStatistics items = readCommonWeekSellStatistics(pm);
			dtos = items.getSellStatistics();
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private WeekSellStatistics readCommonWeekSellStatistics(PersistenceManager pm) {
		Query query = pm.newQuery(WeekSellStatistics.class);
		WeekSellStatistics item;
		List<WeekSellStatistics> items = (List<WeekSellStatistics>) query.execute();
		if (items.size() == 0) {
			item = new WeekSellStatistics();
			item = pm.makePersistent(item);
		} else {
			item = items.get(0);
		}
		return item;
	}

	public List<MonthSellStatistic> readCommonMonthSellStatistics() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<MonthSellStatistic> dtos;
		try {
			MonthSellStatistics items = readCommonMonthSellStatistics(pm);
			dtos = items.getSellStatistics();
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private MonthSellStatistics readCommonMonthSellStatistics(PersistenceManager pm) {
		Query query = pm.newQuery(MonthSellStatistics.class);
		MonthSellStatistics item;
		List<MonthSellStatistics> items = (List<MonthSellStatistics>) query.execute();
		if (items.size() == 0) {
			item = new MonthSellStatistics();
			item = pm.makePersistent(item);
		} else {
			item = items.get(0);
		}
		return item;
	}
	public List<YearSellStatistic> readCommonYearSellStatistics() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<YearSellStatistic> dtos;
		try {
			YearSellStatistics items = readCommonYearSellStatistics(pm);
			dtos = items.getSellStatistics();
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private YearSellStatistics readCommonYearSellStatistics(PersistenceManager pm) {
		Query query = pm.newQuery(YearSellStatistics.class);
		YearSellStatistics item;
		List<YearSellStatistics> items = (List<YearSellStatistics>) query.execute();
		if (items.size() == 0) {
			item = new YearSellStatistics();
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
