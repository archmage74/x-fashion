package com.xfashion.client.statistic.render;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Formatter;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.statistic.PromoStatisticDTO;

public class PromoStatisticTableProvider {

	protected Formatter formatter = Formatter.getInstance();
	protected TextMessages textMessages = GWT.create(TextMessages.class);

	public CellTable<PromoStatisticDTO> createTable() {
		CellTable<PromoStatisticDTO> cellTable = new CellTable<PromoStatisticDTO>(30, GWT.<FilterTableResources> create(FilterTableResources.class));

		cellTable.addColumn(createPromoColumn(), textMessages.statisticPromos());
		cellTable.addColumn(createPiecesColumn(), textMessages.tableHeaderPieces());
		cellTable.addColumn(createTurnoverColumn(), textMessages.tableHeaderTurnover());
		cellTable.addColumn(createProfitColumn(), textMessages.tableHeaderProfit());

		return cellTable;
	}
	
	private Column<PromoStatisticDTO, String> createPromoColumn() {
		Column<PromoStatisticDTO, String> column = new Column<PromoStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(PromoStatisticDTO dto) {
				return dto.getPromoKeyString();
			}
		};
		return column;
	}

	private Column<PromoStatisticDTO, String> createPiecesColumn() {
		Column<PromoStatisticDTO, String> column = new Column<PromoStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(PromoStatisticDTO dto) {
				return textMessages.pieces(dto.getPieces());
			}
		};
		column.setCellStyleNames("statisticValue");
		return column;
	}

	private Column<PromoStatisticDTO, String> createTurnoverColumn() {
		Column<PromoStatisticDTO, String> column = new Column<PromoStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(PromoStatisticDTO dto) {
				return formatter.centsToCurrency(dto.getTurnover());
			}
		};
		column.setCellStyleNames("statisticValue");
		return column;
	}

	private Column<PromoStatisticDTO, String> createProfitColumn() {
		Column<PromoStatisticDTO, String> column = new Column<PromoStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(PromoStatisticDTO dto) {
				return formatter.centsToCurrency(dto.getProfit());
			}
		};
		column.setCellStyleNames("statisticValue");
		return column;
	}

}
