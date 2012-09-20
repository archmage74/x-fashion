package com.xfashion.client.statistic.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.xfashion.client.at.size.SizeDataProvider;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.statistic.SizeStatisticDTO;


public class SizeStatisticComparatorTest extends GWTTestCase {

	public static final String XS = "XS";
	public static final String S = "S";
	public static final String M = "M";
	public static final String L = "L";
	
	@Override
	public String getModuleName() {
		return "com.xfashion.Xfashion";
	}
	
	@Test
	public void testSortingOfFour() {
		List<SizeStatisticDTO> sizeStat = createStatistic(M, S, L, XS);
		
		SizeDataProvider provider = createSizeProvider(XS, S, M, L);
		
		SizeStatisticComparator sizeStatisticComparator = new SizeStatisticComparator(provider);
		Collections.sort(sizeStat, sizeStatisticComparator);
		
		assertEquals(4, sizeStat.size());
		assertEquals(XS, sizeStat.get(0).getSize());
		assertEquals(S, sizeStat.get(1).getSize());
		assertEquals(M, sizeStat.get(2).getSize());
		assertEquals(L, sizeStat.get(3).getSize());
	}

	@Test
	public void testSortingOfNotFound() {
		List<SizeStatisticDTO> sizeStat = createStatistic(S, L, M, XS);
		
		SizeDataProvider provider = createSizeProvider(XS, S, L);
		
		SizeStatisticComparator sizeStatisticComparator = new SizeStatisticComparator(provider);
		Collections.sort(sizeStat, sizeStatisticComparator);
		
		assertEquals(4, sizeStat.size());
		assertEquals(XS, sizeStat.get(0).getSize());
		assertEquals(S, sizeStat.get(1).getSize());
		assertEquals(L, sizeStat.get(2).getSize());
		assertEquals(M, sizeStat.get(3).getSize());
	}
	
	@Test
	public void testSortingOfNull() {
		List<SizeStatisticDTO> sizeStat = createStatistic(S, L, null, XS);
		
		SizeDataProvider provider = createSizeProvider(XS, S, L);
		
		SizeStatisticComparator sizeStatisticComparator = new SizeStatisticComparator(provider);
		Collections.sort(sizeStat, sizeStatisticComparator);
		
		assertEquals(4, sizeStat.size());
		assertEquals(XS, sizeStat.get(0).getSize());
		assertEquals(S, sizeStat.get(1).getSize());
		assertEquals(L, sizeStat.get(2).getSize());
		assertEquals(null, sizeStat.get(3).getSize());
	}
	
	private SizeDataProvider createSizeProvider(String...sizes) {
		SizeDataProvider provider = new SizeDataProvider(null, new SimpleEventBus());
		for (String s : sizes) {
			SizeDTO size = new SizeDTO(s);
			provider.getAllItems().add(size);
		}
		return provider;
	}
	
	private List<SizeStatisticDTO> createStatistic(String...sizes) {
		List<SizeStatisticDTO> stats = new ArrayList<SizeStatisticDTO>();
		for (String s : sizes) {
			SizeStatisticDTO stat = new SizeStatisticDTO();
			stat.setSize(s);
			stats.add(stat);
		}
		return stats;
	}
}
