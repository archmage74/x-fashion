package com.xfashion.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import junit.framework.Assert;

import org.junit.Test;

import com.xfashion.server.statistic.DaySellStatistic;
import com.xfashion.server.statistic.StatisticAdder;
import com.xfashion.shared.SoldArticleDTO;

public class StatisticAdderTest {

	private StatisticAdder statisticAdder = new StatisticAdder();

	private List<DaySellStatistic> createDaySellStatisticList() {
		List<DaySellStatistic> statistics = new ArrayList<DaySellStatistic>();
		statistics.add(createDaySellStatistic(5, 9, 93, 92000, 9100));
		statistics.add(createDaySellStatistic(5, 8, 83, 82000, 8100));
		statistics.add(createDaySellStatistic(5, 7, 73, 72000, 7100));
		statistics.add(createDaySellStatistic(5, 5, 53, 52000, 5100));
		return statistics;
	}
	
	private DaySellStatistic createDaySellStatistic(int month, int day, int pieces, int turnover, int profit) {
		GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("Europe/Vienna"));
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);

		gc.set(Calendar.YEAR, 2012);
		gc.set(Calendar.MONTH, month);
		gc.set(Calendar.DAY_OF_MONTH, day);
		
		DaySellStatistic dss = new DaySellStatistic(gc.getTime());
		dss.setPieces(pieces);
		dss.setTurnover(turnover);
		dss.setProfit(profit);

		return dss;
	}
	
	private SoldArticleDTO createSoldArticle(int month, int day, int amount, int sellPrice, int buyPrice) {
		SoldArticleDTO sa = new SoldArticleDTO();
		GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("Europe/Vienna"));
		gc.set(2012, month, day, 17, 42, 12);
		sa.setSellDate(gc.getTime());
		sa.setAmount(amount);
		sa.setSellPrice(sellPrice);
		sa.setBuyPrice(buyPrice);
		return sa;
	}
	
	@Test
	public void addDayStatisticTest1() {
		List<DaySellStatistic> statistics = new ArrayList<DaySellStatistic>();
		SoldArticleDTO soldArticle = createSoldArticle(5, 8, 1, 1200, 600);
		statisticAdder.addToStatistic(DaySellStatistic.class, statistics, soldArticle);
		
		Integer expectedPieces = new Integer(1);
		Integer expectedTurnover = new Integer(1200);
		Integer expectedProfit = new Integer(600);
		Assert.assertEquals(1, statistics.size());
		DaySellStatistic actual = statistics.get(0);
		Assert.assertEquals(expectedPieces, actual.getPieces());
		Assert.assertEquals(expectedTurnover, actual.getTurnover());
		Assert.assertEquals(expectedProfit, actual.getProfit());
	}

	@Test
	public void addDayStatisticTest2() {
		List<DaySellStatistic> statistics = createDaySellStatisticList();
		SoldArticleDTO soldArticle = createSoldArticle(5, 9, 1, 1200, 600);
		statisticAdder.addToStatistic(DaySellStatistic.class, statistics, soldArticle);
		
		Integer expectedPieces = new Integer(94);
		Integer expectedTurnover = new Integer(92000 + 1200);
		Integer expectedProfit = new Integer(9100 + 600);
		Assert.assertEquals(4, statistics.size());
		DaySellStatistic actual = statistics.get(0);
		Assert.assertEquals(expectedPieces, actual.getPieces());
		Assert.assertEquals(expectedTurnover, actual.getTurnover());
		Assert.assertEquals(expectedProfit, actual.getProfit());
	}

	@Test
	public void addDayStatisticTest3() {
		List<DaySellStatistic> statistics = createDaySellStatisticList();
		SoldArticleDTO soldArticle = createSoldArticle(5, 8, 1, 1200, 600);
		statisticAdder.addToStatistic(DaySellStatistic.class, statistics, soldArticle);
		
		Integer expectedPieces = new Integer(84);
		Integer expectedTurnover = new Integer(82000 + 1200);
		Integer expectedProfit = new Integer(8100 + 600);
		Assert.assertEquals(4, statistics.size());
		DaySellStatistic actual = statistics.get(1);
		Assert.assertEquals(expectedPieces, actual.getPieces());
		Assert.assertEquals(expectedTurnover, actual.getTurnover());
		Assert.assertEquals(expectedProfit, actual.getProfit());
	}

	@Test
	public void addDayStatisticTest4() {
		List<DaySellStatistic> statistics = createDaySellStatisticList();
		SoldArticleDTO soldArticle = createSoldArticle(5, 7, 1, 1200, 600);
		statisticAdder.addToStatistic(DaySellStatistic.class, statistics, soldArticle);
		
		Integer expectedPieces = new Integer(74);
		Integer expectedTurnover = new Integer(72000 + 1200);
		Integer expectedProfit = new Integer(7100 + 600);
		Assert.assertEquals(4, statistics.size());
		DaySellStatistic actual = statistics.get(2);
		Assert.assertEquals(expectedPieces, actual.getPieces());
		Assert.assertEquals(expectedTurnover, actual.getTurnover());
		Assert.assertEquals(expectedProfit, actual.getProfit());
	}

	@Test
	public void addDayStatisticTest5() {
		List<DaySellStatistic> statistics = createDaySellStatisticList();
		SoldArticleDTO soldArticle = createSoldArticle(5, 6, 1, 1200, 600);
		statisticAdder.addToStatistic(DaySellStatistic.class, statistics, soldArticle);
		
		Integer expectedPieces = new Integer(1);
		Integer expectedTurnover = new Integer(1200);
		Integer expectedProfit = new Integer(600);
		Assert.assertEquals(5, statistics.size());
		DaySellStatistic actual = statistics.get(3);
		Assert.assertEquals(expectedPieces, actual.getPieces());
		Assert.assertEquals(expectedTurnover, actual.getTurnover());
		Assert.assertEquals(expectedProfit, actual.getProfit());
	}

	@Test
	public void addDayStatisticTest6() {
		List<DaySellStatistic> statistics = createDaySellStatisticList();
		SoldArticleDTO soldArticle = createSoldArticle(5, 5, 1, 1200, 600);
		statisticAdder.addToStatistic(DaySellStatistic.class, statistics, soldArticle);
		
		Integer expectedPieces = new Integer(54);
		Integer expectedTurnover = new Integer(52000 + 1200);
		Integer expectedProfit = new Integer(5100 + 600);
		Assert.assertEquals(4, statistics.size());
		DaySellStatistic actual = statistics.get(3);
		Assert.assertEquals(expectedPieces, actual.getPieces());
		Assert.assertEquals(expectedTurnover, actual.getTurnover());
		Assert.assertEquals(expectedProfit, actual.getProfit());
	}

	@Test
	public void addDayStatisticTest7() {
		List<DaySellStatistic> statistics = createDaySellStatisticList();
		SoldArticleDTO soldArticle = createSoldArticle(5, 4, 1, 1200, 600);
		statisticAdder.addToStatistic(DaySellStatistic.class, statistics, soldArticle);
		
		Integer expectedPieces = new Integer(1);
		Integer expectedTurnover = new Integer(1200);
		Integer expectedProfit = new Integer(600);
		Assert.assertEquals(5, statistics.size());
		DaySellStatistic actual = statistics.get(4);
		Assert.assertEquals(expectedPieces, actual.getPieces());
		Assert.assertEquals(expectedTurnover, actual.getTurnover());
		Assert.assertEquals(expectedProfit, actual.getProfit());
	}

	@Test
	public void addDayStatisticTest8() {
		List<DaySellStatistic> statistics = createDaySellStatisticList();
		SoldArticleDTO soldArticle = createSoldArticle(5, 12, 1, 1200, 600);
		statisticAdder.addToStatistic(DaySellStatistic.class, statistics, soldArticle);
		
		Integer expectedPieces = new Integer(1);
		Integer expectedTurnover = new Integer(1200);
		Integer expectedProfit = new Integer(600);
		Assert.assertEquals(5, statistics.size());
		DaySellStatistic actual = statistics.get(0);
		Assert.assertEquals(expectedPieces, actual.getPieces());
		Assert.assertEquals(expectedTurnover, actual.getTurnover());
		Assert.assertEquals(expectedProfit, actual.getProfit());
	}

	@Test
	public void addDayStatisticTest9() {
		List<DaySellStatistic> statistics = createDaySellStatisticList();
		SoldArticleDTO soldArticle = createSoldArticle(5, 1, 1, 1200, 600);
		statisticAdder.addToStatistic(DaySellStatistic.class, statistics, soldArticle);
		
		Integer expectedPieces = new Integer(1);
		Integer expectedTurnover = new Integer(1200);
		Integer expectedProfit = new Integer(600);
		Assert.assertEquals(5, statistics.size());
		DaySellStatistic actual = statistics.get(4);
		Assert.assertEquals(expectedPieces, actual.getPieces());
		Assert.assertEquals(expectedTurnover, actual.getTurnover());
		Assert.assertEquals(expectedProfit, actual.getProfit());
	}

}
