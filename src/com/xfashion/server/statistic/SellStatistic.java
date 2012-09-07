package com.xfashion.server.statistic;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.xfashion.shared.SellStatisticDTO;
import com.xfashion.shared.SoldArticleDTO;

public abstract class SellStatistic {

	public SellStatistic() {
		this(new Date());
	}
	
	public SellStatistic(Date startDate) {
		init(startDate);
	}
	
	/**
	 * Sets the date of the given calendar to the data of the period 
	 * start of the period containing the current date.
	 * The time of the calendar has to be set seperatly, e.g. with {@link timeToZero(GregorianCalendar gc)}.  
	 * @param gc Calendar to manipulate.
	 */
	abstract protected void dayToPeriodStart(GregorianCalendar gc);
	
	/**
	 * adds the duration of one time period to the given calendar 
	 * @param gc
	 */
	abstract protected void addPeriod(GregorianCalendar gc);
	
	public abstract String getKeyString();
	
	public abstract Date getStartDate();

	public abstract void setStartDate(Date startDate);

	public abstract Integer getPieces();

	public abstract void setPieces(Integer pieces);

	public abstract Integer getTurnover();

	public abstract void setTurnover(Integer turnover);

	public abstract Integer getProfit();

	public abstract void setProfit(Integer profit);
	
	public void init(Date date) {
		GregorianCalendar gc = createCalendar();
		gc.setTime(date);
		timeToZero(gc);
		dayToPeriodStart(gc);
		setStartDate(gc.getTime());
		setPieces(new Integer(0));
		setTurnover(new Integer(0));
		setProfit(new Integer(0));
	}

	public void add(SoldArticleDTO soldArticle) {
		setPieces(getPieces() + soldArticle.getAmount());
		setTurnover(getTurnover() + soldArticle.getSellPrice());
		setProfit(getProfit() + soldArticle.getSellPrice() - soldArticle.getBuyPrice());
	}

	public boolean isWithinPeriod(Date sellDate) {
		long check = sellDate.getTime();
		GregorianCalendar gc = createCalendar();
		gc.setTime(getStartDate());
		long start = gc.getTime().getTime();
		addPeriod(gc);
		long end = gc.getTime().getTime();
		if (start <= check && end > check) {
			return true;
		} else {
			return false;
		}
	}
	
	protected void fillDTO(SellStatisticDTO dto) {
		dto.setKeyString(getKeyString());
		dto.setStartDate(getStartDate());
		dto.setPieces(getPieces());
		dto.setTurnover(getTurnover());
		dto.setProfit(getProfit());
	}
	
	protected void timeToZero(GregorianCalendar gc) {
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);
	}

	private GregorianCalendar createCalendar() {
		GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("Europe/Vienna"));
		gc.setFirstDayOfWeek(Calendar.MONDAY);
		return gc;
	}
	
}
