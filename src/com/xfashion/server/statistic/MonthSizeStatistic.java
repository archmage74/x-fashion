package com.xfashion.server.statistic;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class MonthSizeStatistic extends ASizeStatistic {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String size;
	
	@Persistent
	private Integer pieces;

	@Override
	public Key getKey() {
		return key;
	}
	
	public MonthSizeStatistic(String size) {
		this.size = size;
		this.pieces = 0;
	}
	
	@Override
	public String getSize() {
		return size;
	}

	@Override
	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public Integer getPieces() {
		return pieces;
	}

	@Override
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

}
