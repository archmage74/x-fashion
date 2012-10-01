package com.xfashion.server.statistic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.statistic.CategoryStatisticDTO;
import com.xfashion.shared.statistic.PromoStatisticDTO;
import com.xfashion.shared.statistic.SellStatisticDTO;
import com.xfashion.shared.statistic.SizeStatisticDTO;
import com.xfashion.shared.statistic.TopStatisticDTO;

public abstract class SellStatistic <S extends ASizeStatistic, C extends ACategoryStatistic, P extends APromoStatistic, T extends ATopStatistic> {

	protected static PeriodHelper periodHelper = new PeriodHelper();
	
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

	public abstract List<S> getSizeStatistics();
	
	public abstract List<C> getCategoryStatistics();
	
	public abstract List<P> getPromoStatistics();
	
	public abstract List<T> getTopStatistics();
	
	public abstract SellStatisticDTO createDTO();
	
	/** 
	 * Initializes the SellStatistic so that its period start is set to the period that contains the given date. 
	 * @param date
	 */
	public void init(Date date) {
		GregorianCalendar gc = periodHelper.createCalendar();
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
		GregorianCalendar gc = periodHelper.createCalendar();
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
	
	public List<SizeStatisticDTO> createSizeStatisticDTOs() {
		List<SizeStatisticDTO> dtos = new ArrayList<SizeStatisticDTO>();
		for (S s : getSizeStatistics()) {
			dtos.add(s.createDTO());
		}
		return dtos;
	}
	
	public List<CategoryStatisticDTO> createCategoryStatisticDTOs() {
		List<CategoryStatisticDTO> dtos = new ArrayList<CategoryStatisticDTO>();
		for (C s : getCategoryStatistics()) {
			dtos.add(s.createDTO());
		}
		return dtos;
	}
	
	public List<PromoStatisticDTO> createPromoStatisticDTOs() {
		List<PromoStatisticDTO> dtos = new ArrayList<PromoStatisticDTO>();
		for (P s : getPromoStatistics()) {
			dtos.add(s.createDTO());
		}
		return dtos;
	}
	
	public List<TopStatisticDTO> createTopStatisticDTOs() {
		List<TopStatisticDTO> dtos = new ArrayList<TopStatisticDTO>();
		for (T s : getTopStatistics()) {
			dtos.add(s.createDTO());
		}
		return dtos;
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

	protected <D extends IStatisticDetail> void addToDetailList(List<D> list, SoldArticleDTO soldArticleDTO, Class<D> detailClass) {
		D detail = null;
		for (D s : list) {
			if (s.matchesStatistic(soldArticleDTO)) {
				detail = s;
				break;
			}
		}
		if (detail == null) {
			try {
				detail = detailClass.newInstance();
				if (!detail.isRelevant(soldArticleDTO)) {
					return;
				}
				detail.initFromSoldArticleDTO(soldArticleDTO);
				list.add(detail);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		detail.addToStatistic(soldArticleDTO);
	}

}
