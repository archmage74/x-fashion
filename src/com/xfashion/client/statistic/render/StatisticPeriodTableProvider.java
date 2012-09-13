package com.xfashion.client.statistic.render;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.ibm.icu.util.Calendar;
import com.xfashion.client.Formatter;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.statistic.SellStatisticDTO;

public class StatisticPeriodTableProvider {

	protected CellTable<? extends SellStatisticDTO> cellTable;
	
	protected Formatter formatter = Formatter.getInstance();
	protected TextMessages textMessages = GWT.create(TextMessages.class);

	public CellTable<SellStatisticDTO> createTable() {
		CellTable<SellStatisticDTO> cellTable = new CellTable<SellStatisticDTO>(30);

		// TODO add header (in columns?)
		
		cellTable.addColumn(createDayPeriodColumn(), textMessages.dayButton());
		cellTable.addColumn(createPiecesColumn(), textMessages.tableHeaderPieces());
		cellTable.addColumn(createTurnoverColumn(), textMessages.tableHeaderTurnover());
		cellTable.addColumn(createProfitColumn(), textMessages.tableHeaderProfit());

		return cellTable;
	}
	
	public void changePeriodType(CellTable<SellStatisticDTO> cellTable, int periodType) {
		Column<SellStatisticDTO, String> newColumn = null;
		String header = null;
		switch(periodType) {
		case Calendar.DATE:
			newColumn = createDayPeriodColumn();
			header = textMessages.dayButton();
			break;
		case Calendar.WEEK_OF_YEAR:
			newColumn = createWeekPeriodColumn();
			header = textMessages.weekButton();
			break;
		case Calendar.MONTH:
			newColumn = createMonthPeriodColumn();
			header = textMessages.monthButton();
			break;
		case Calendar.YEAR:
			newColumn = createYearPeriodColumn();
			header = textMessages.yearButton();
			break;
		}
		cellTable.removeColumn(0);
		cellTable.insertColumn(0, newColumn, header);
	}
	
	private Column<SellStatisticDTO, String> createDayPeriodColumn() {
		Column<SellStatisticDTO, String> column = new Column<SellStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(SellStatisticDTO dto) {
				return textMessages.day(dto.getStartDate());
			}
		};
		return column;
	}

	private Column<SellStatisticDTO, String> createWeekPeriodColumn() {
		Column<SellStatisticDTO, String> column = new Column<SellStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(SellStatisticDTO dto) {
				return formatter.week(dto.getStartDate());
			}
		};
		return column;
	}

	private Column<SellStatisticDTO, String> createMonthPeriodColumn() {
		Column<SellStatisticDTO, String> column = new Column<SellStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(SellStatisticDTO dto) {
				return textMessages.month(dto.getStartDate());
			}
		};
		return column;
	}

	private Column<SellStatisticDTO, String> createYearPeriodColumn() {
		Column<SellStatisticDTO, String> column = new Column<SellStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(SellStatisticDTO dto) {
				return textMessages.year(dto.getStartDate());
			}
		};
		return column;
	}

	private Column<SellStatisticDTO, String> createPiecesColumn() {
		Column<SellStatisticDTO, String> column = new Column<SellStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(SellStatisticDTO dto) {
				return textMessages.pieces(dto.getAmount());
			}
		};
		column.setCellStyleNames("statisticValue");
		return column;
	}

	private Column<SellStatisticDTO, String> createTurnoverColumn() {
		Column<SellStatisticDTO, String> column = new Column<SellStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(SellStatisticDTO dto) {
				return formatter.centsToCurrency(dto.getTurnover());
			}
		};
		column.setCellStyleNames("statisticValue");
		return column;
	}

	private Column<SellStatisticDTO, String> createProfitColumn() {
		Column<SellStatisticDTO, String> column = new Column<SellStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(SellStatisticDTO dto) {
				return formatter.centsToCurrency(dto.getProfit());
			}
		};
		column.setCellStyleNames("statisticValue");
		return column;
	}

}
