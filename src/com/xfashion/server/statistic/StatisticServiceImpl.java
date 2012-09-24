package com.xfashion.server.statistic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.backends.BackendService;
import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.statistic.StatisticService;
import com.xfashion.server.PMF;
import com.xfashion.server.SoldArticle;
import com.xfashion.server.user.Shop;
import com.xfashion.server.user.UserServiceImpl;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.statistic.CategoryStatisticDTO;
import com.xfashion.shared.statistic.DaySellStatisticDTO;
import com.xfashion.shared.statistic.MonthSellStatisticDTO;
import com.xfashion.shared.statistic.PromoStatisticDTO;
import com.xfashion.shared.statistic.SellStatisticDTO;
import com.xfashion.shared.statistic.SizeStatisticDTO;
import com.xfashion.shared.statistic.TopStatisticDTO;
import com.xfashion.shared.statistic.WeekSellStatisticDTO;
import com.xfashion.shared.statistic.YearSellStatisticDTO;

public class StatisticServiceImpl extends RemoteServiceServlet implements StatisticService {

	private static final long serialVersionUID = 1L;

	private StatisticAdder adder = new StatisticAdder();

	private PeriodHelper periodHelper = new PeriodHelper();
	private UserServiceImpl userService = new UserServiceImpl();
	
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
	public List<WeekSellStatisticDTO> readCommonWeekSellStatistic(int from, int to) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<WeekSellStatisticDTO> dtos = new ArrayList<WeekSellStatisticDTO>();
		try {
			SellStatistics sellStatistics = readCommonSellStatistics(pm);
			List<WeekSellStatistic> stats = sellStatistics.getWeekSellStatistics(); 
			for (int index = from; index < to && index < stats.size(); index++) {
				dtos.add(stats.get(index).createDTO());
			}
		} finally {
			pm.close();
		}
		return dtos;
	}
	
	@Override
	public List<MonthSellStatisticDTO> readCommonMonthSellStatistic(int from, int to) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<MonthSellStatisticDTO> dtos = new ArrayList<MonthSellStatisticDTO>();
		try {
			SellStatistics sellStatistics = readCommonSellStatistics(pm);
			List<MonthSellStatistic> stats = sellStatistics.getMonthSellStatistics(); 
			for (int index = from; index < to && index < stats.size(); index++) {
				dtos.add(stats.get(index).createDTO());
			}
		} finally {
			pm.close();
		}
		return dtos;
	}
	
	@Override
	public List<YearSellStatisticDTO> readCommonYearSellStatistic(int from, int to) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<YearSellStatisticDTO> dtos = new ArrayList<YearSellStatisticDTO>();
		try {
			SellStatistics sellStatistics = readCommonSellStatistics(pm);
			List<YearSellStatistic> stats = sellStatistics.getYearSellStatistics(); 
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
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.setOptimistic(false);
			tx.begin();
			
			SellStatistics statistics = readCommonSellStatistics(pm);

			adder.addToStatistic(DaySellStatistic.class, statistics.getDaySellStatistics(), soldArticle);
			adder.addToStatistic(WeekSellStatistic.class, statistics.getWeekSellStatistics(), soldArticle);
			adder.addToStatistic(MonthSellStatistic.class, statistics.getMonthSellStatistics(), soldArticle);
			adder.addToStatistic(YearSellStatistic.class, statistics.getYearSellStatistics(), soldArticle);

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
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
		Transaction tx = pm.currentTransaction();
		try {
			tx.setOptimistic(false);
			tx.begin();
			SellStatistics sellStatistics = readCommonSellStatistics(pm);
			pm.deletePersistent(sellStatistics);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	@Override
	public void rewriteStatistic() {
		deleteAllStatistics();
		
		BackendService backendService = BackendServiceFactory.getBackendService();
		String url = backendService.getBackendAddress("rewritestatistic");
		Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions options = TaskOptions.Builder.withUrl("/backend/rewritestatisticbackend")
			.header("Host", url);
			// .param(UpdateSellStatisticServlet.PARAM_SOLD_ARTICLE_KEY, soldArticle.getKeyAsString()));
		queue.add(options);
	}
	
	private void deleteAllStatistics() {
		deleteAllCommonStatistics();
	}

	@Override
	public List<SizeStatisticDTO> readSizeStatistic(SellStatisticDTO sellStatisticDTO) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<SizeStatisticDTO> dtos = new ArrayList<SizeStatisticDTO>();
		try {
			SellStatistic<?, ?, ?, ?> sellStatistic = readSellStatistic(pm, sellStatisticDTO);
			dtos = sellStatistic.createSizeStatisticDTOs();
		} finally {
			pm.close();
		}
		return dtos;
	}

	@Override
	public List<CategoryStatisticDTO> readCategoryStatistic(SellStatisticDTO sellStatisticDTO) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<CategoryStatisticDTO> dtos = new ArrayList<CategoryStatisticDTO>();
		try {
			SellStatistic<?, ?, ?, ?> sellStatistic = readSellStatistic(pm, sellStatisticDTO);
			dtos = sellStatistic.createCategoryStatisticDTOs();
		} finally {
			pm.close();
		}
		return dtos;
	}

	@Override
	public List<PromoStatisticDTO> readPromoStatistic(SellStatisticDTO sellStatisticDTO) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<PromoStatisticDTO> dtos = new ArrayList<PromoStatisticDTO>();
		try {
			SellStatistic<?, ?, ?, ?> sellStatistic = readSellStatistic(pm, sellStatisticDTO);
			dtos = sellStatistic.createPromoStatisticDTOs();
		} finally {
			pm.close();
		}
		return dtos;
	}

	@Override
	public List<TopStatisticDTO> readTopStatistic(SellStatisticDTO sellStatisticDTO) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<TopStatisticDTO> dtos = new ArrayList<TopStatisticDTO>();
		try {
			SellStatistic<?, ?, ?, ?> sellStatistic = readSellStatistic(pm, sellStatisticDTO);
			dtos = sellStatistic.createTopStatisticDTOs();
		} finally {
			pm.close();
		}
		return dtos;
	}

	private SellStatistic<?, ?, ?, ?> readSellStatistic(PersistenceManager pm, SellStatisticDTO sellStatisticDTO) {
		Key key = KeyFactory.stringToKey(sellStatisticDTO.getKeyString());
		SellStatistic<?, ?, ?, ?> sellStatistic = null;
		if (sellStatisticDTO instanceof DaySellStatisticDTO) {
			sellStatistic = pm.getObjectById(DaySellStatistic.class, key);
		} else if (sellStatisticDTO instanceof WeekSellStatisticDTO) {
			sellStatistic = pm.getObjectById(WeekSellStatistic.class, key);
		} else if (sellStatisticDTO instanceof MonthSellStatisticDTO) {
			sellStatistic = pm.getObjectById(MonthSellStatistic.class, key);
		} else if (sellStatisticDTO instanceof YearSellStatisticDTO) {
			sellStatistic = pm.getObjectById(YearSellStatistic.class, key);
		}
		return sellStatistic;
	}

	@Override
	public SoldArticleDTO readSoldArticle(String keyString) throws IllegalArgumentException {
		SoldArticleDTO dto = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			SoldArticle soldArticle = readSoldArticle(pm, keyString);
			dto = soldArticle.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	private SoldArticle readSoldArticle(PersistenceManager pm, String keyString) {
		SoldArticle soldArticle = pm.getObjectById(SoldArticle.class, KeyFactory.stringToKey(keyString));
		return soldArticle;
	}

	@Override
	public List<SoldArticleDTO> readSoldArticles(int from, int to) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<SoldArticleDTO> dtos = new ArrayList<SoldArticleDTO>();
		try {
			Collection<SoldArticle> soldArticles = readSoldArticles(pm, from, to);
			for (SoldArticle soldArticle : soldArticles) {
				dtos.add(soldArticle.createDTO());
			}
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private List<SoldArticle> readSoldArticles(PersistenceManager pm, int from, int to) {
		Query soldArticleQuery = pm.newQuery(SoldArticle.class);
		soldArticleQuery.setRange(from, to);
		soldArticleQuery.setOrdering("sellDate desc");
		List<SoldArticle> soldArticles = (List<SoldArticle>) soldArticleQuery.execute();
		return soldArticles;
	}

	@Override
	public List<SoldArticleDTO> readSoldArticles(SellStatisticDTO sellStatisticDTO, int fromIndex, int toIndex) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<SoldArticleDTO> dtos = new ArrayList<SoldArticleDTO>();
		DateRange range = periodHelper.getDateRange(sellStatisticDTO.getStartDate(), sellStatisticDTO.getPeriodType());
		try {
			Collection<SoldArticle> soldArticles = readSoldArticles(pm, range.getStartDate(), range.getEndDate(), fromIndex, toIndex);
			for (SoldArticle soldArticle : soldArticles) {
				dtos.add(soldArticle.createDTO());
			}
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private List<SoldArticle> readSoldArticles(PersistenceManager pm, Date fromDate, Date toDate, int fromIndex, int toIndex) {
		Query soldArticleQuery = pm.newQuery(SoldArticle.class);
		soldArticleQuery.setFilter("sellDate >= fromDateParam && sellDate <= toDateParam");
		soldArticleQuery.declareParameters("java.util.Date fromDateParam, java.util.Date toDateParam");
		soldArticleQuery.setRange(fromIndex, toIndex);
		soldArticleQuery.setOrdering("sellDate desc");
		List<SoldArticle> soldArticles = (List<SoldArticle>) soldArticleQuery.execute(fromDate, toDate);
		return soldArticles;
	}

	@Override
	public List<SoldArticleDTO> readSoldArticlesOfShop(String shopKey, int from, int to) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<SoldArticleDTO> dtos = new ArrayList<SoldArticleDTO>();
		try {
			Shop shop = userService.readShop(pm, shopKey);
			int cnt = (int) from;
			List<SoldArticle> soldArticles = shop.getSoldArticles();
			while (cnt < to && cnt < soldArticles.size()) {
				dtos.add(soldArticles.get(cnt).createDTO());
				cnt++;
			}
		} finally {
			pm.close();
		}
		return dtos;
	}

	@Override
	public List<SoldArticleDTO> readOwnSoldArticles(int from, int to) throws IllegalArgumentException {
		UserDTO userDTO = userService.getOwnUser();
		
		return readSoldArticlesOfShop(userDTO.getShop().getKeyString(), from, to);
	}

}
