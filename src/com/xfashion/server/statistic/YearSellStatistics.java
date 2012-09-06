package com.xfashion.server.statistic;

import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class YearSellStatistics {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="startDate desc"))
	private List<YearSellStatistic> sellStatistics;

	public Key getKey() {
		return key;
	}
	
	public List<YearSellStatistic> getSellStatistics() {
		return sellStatistics;
	}

	public void setSellStatistics(List<YearSellStatistic> sellStatistics) {
		this.sellStatistics = sellStatistics;
	}
}
