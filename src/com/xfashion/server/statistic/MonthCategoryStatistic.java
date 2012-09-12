package com.xfashion.server.statistic;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class MonthCategoryStatistic extends ACategoryStatistic {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	private String category;
	
	private Integer pieces;

	private Integer turnover;
	
	private Integer profit;
	
	/* (non-Javadoc)
	 * @see com.xfashion.server.statistic.ACategoryStatistic#getKey()
	 */
	@Override
	public Key getKey() {
		return key;
	}

	/* (non-Javadoc)
	 * @see com.xfashion.server.statistic.ACategoryStatistic#getCategory()
	 */
	@Override
	public String getCategory() {
		return category;
	}

	/* (non-Javadoc)
	 * @see com.xfashion.server.statistic.ACategoryStatistic#setCategory(java.lang.String)
	 */
	@Override
	public void setCategory(String category) {
		this.category = category;
	}

	/* (non-Javadoc)
	 * @see com.xfashion.server.statistic.ACategoryStatistic#getPieces()
	 */
	@Override
	public Integer getPieces() {
		return pieces;
	}

	/* (non-Javadoc)
	 * @see com.xfashion.server.statistic.ACategoryStatistic#setPieces(java.lang.Integer)
	 */
	@Override
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

	/* (non-Javadoc)
	 * @see com.xfashion.server.statistic.ACategoryStatistic#getTurnover()
	 */
	@Override
	public Integer getTurnover() {
		return turnover;
	}

	/* (non-Javadoc)
	 * @see com.xfashion.server.statistic.ACategoryStatistic#setTurnover(java.lang.Integer)
	 */
	@Override
	public void setTurnover(Integer turnover) {
		this.turnover = turnover;
	}

	/* (non-Javadoc)
	 * @see com.xfashion.server.statistic.ACategoryStatistic#getProfit()
	 */
	@Override
	public Integer getProfit() {
		return profit;
	}

	/* (non-Javadoc)
	 * @see com.xfashion.server.statistic.ACategoryStatistic#setProfit(java.lang.Integer)
	 */
	@Override
	public void setProfit(Integer profit) {
		this.profit = profit;
	}

}
