package com.xfashion.client.statistic.render;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Formatter;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.statistic.CategoryStatisticDTO;

public class CategoryStatisticTableProvider {

	protected Formatter formatter = Formatter.getInstance();
	protected TextMessages textMessages = GWT.create(TextMessages.class);

	public CellTable<CategoryStatisticDTO> createTable() {
		CellTable<CategoryStatisticDTO> cellTable = new CellTable<CategoryStatisticDTO>(30, GWT.<FilterTableResources> create(FilterTableResources.class));

		cellTable.addColumn(createCategoryColumn(), textMessages.tableHeaderCategory());
		cellTable.addColumn(createPiecesColumn(), textMessages.tableHeaderPieces());
		cellTable.addColumn(createTurnoverColumn(), textMessages.tableHeaderTurnover());
		cellTable.addColumn(createProfitColumn(), textMessages.tableHeaderProfit());
		cellTable.addColumn(createPercentColumn(), textMessages.tableHeaderPercent());

		return cellTable;
	}
	
	private Column<CategoryStatisticDTO, String> createCategoryColumn() {
		Column<CategoryStatisticDTO, String> column = new Column<CategoryStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(CategoryStatisticDTO dto) {
				return dto.getCategory();
			}
		};
		return column;
	}

	private Column<CategoryStatisticDTO, String> createPiecesColumn() {
		Column<CategoryStatisticDTO, String> column = new Column<CategoryStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(CategoryStatisticDTO dto) {
				return textMessages.pieces(dto.getPieces());
			}
		};
		column.setCellStyleNames("statisticValue");
		return column;
	}

	private Column<CategoryStatisticDTO, String> createTurnoverColumn() {
		Column<CategoryStatisticDTO, String> column = new Column<CategoryStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(CategoryStatisticDTO dto) {
				return formatter.centsToCurrency(dto.getTurnover());
			}
		};
		column.setCellStyleNames("statisticValue");
		return column;
	}

	private Column<CategoryStatisticDTO, String> createProfitColumn() {
		Column<CategoryStatisticDTO, String> column = new Column<CategoryStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(CategoryStatisticDTO dto) {
				return formatter.centsToCurrency(dto.getProfit());
			}
		};
		column.setCellStyleNames("statisticValue");
		return column;
	}

	private Column<CategoryStatisticDTO, String> createPercentColumn() {
		Column<CategoryStatisticDTO, String> column = new Column<CategoryStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(CategoryStatisticDTO dto) {
				if (dto.getPercent() != null) {
					return textMessages.percentNoDecimal(dto.getPercent());
				} else {
					return textMessages.notAvailable();
				}
			}
		};
		column.setCellStyleNames("statisticValue");
		return column;
	}


}
