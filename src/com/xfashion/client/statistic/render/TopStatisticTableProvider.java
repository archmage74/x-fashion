package com.xfashion.client.statistic.render;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Formatter;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.statistic.TopStatisticDTO;

public class TopStatisticTableProvider {

	protected Formatter formatter = Formatter.getInstance();
	protected TextMessages textMessages = GWT.create(TextMessages.class);

	public CellTable<TopStatisticDTO> createTable() {
		CellTable<TopStatisticDTO> cellTable = new CellTable<TopStatisticDTO>(30, GWT.<FilterTableResources> create(FilterTableResources.class));

		cellTable.addColumn(createCategoryColumn(), textMessages.tableHeaderCategory());
		cellTable.addColumn(createNameColumn(), textMessages.tableHeaderArticle());
		cellTable.addColumn(createPiecesColumn(), textMessages.tableHeaderPieces());

		return cellTable;
	}
	
	private Column<TopStatisticDTO, String> createCategoryColumn() {
		Column<TopStatisticDTO, String> column = new Column<TopStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(TopStatisticDTO dto) {
				return dto.getCategory();
			}
		};
		return column;
	}

	private Column<TopStatisticDTO, String> createNameColumn() {
		Column<TopStatisticDTO, String> column = new Column<TopStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(TopStatisticDTO dto) {
				return dto.getArticleName();
			}
		};
		return column;
	}

	private Column<TopStatisticDTO, String> createPiecesColumn() {
		Column<TopStatisticDTO, String> column = new Column<TopStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(TopStatisticDTO dto) {
				return textMessages.pieces(dto.getPieces());
			}
		};
		column.setCellStyleNames("statisticValue");
		return column;
	}

}
