package com.xfashion.server.statistic;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class WeekTopStatistic extends ATopStatistic {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	private String category;
	
	private String articleName;
	
	private Integer pieces;

	@Override
	public Key getKey() {
		return key;
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String getArticleName() {
		return articleName;
	}

	@Override
	public void setArticleName(String articleName) {
		this.articleName = articleName;
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
