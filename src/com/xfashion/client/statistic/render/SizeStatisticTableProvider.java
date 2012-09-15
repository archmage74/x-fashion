package com.xfashion.client.statistic.render;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Formatter;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.statistic.SizeStatisticDTO;

public class SizeStatisticTableProvider {

	protected Formatter formatter = Formatter.getInstance();
	protected TextMessages textMessages = GWT.create(TextMessages.class);

	public CellTable<SizeStatisticDTO> createTable() {
		CellTable<SizeStatisticDTO> cellTable = new CellTable<SizeStatisticDTO>(30, GWT.<FilterTableResources> create(FilterTableResources.class));

		cellTable.addColumn(createSizeColumn(), textMessages.tableHeaderSize());
		cellTable.addColumn(createPiecesColumn(), textMessages.tableHeaderPieces());

		return cellTable;
	}
	
	
	private Column<SizeStatisticDTO, String> createSizeColumn() {
		Column<SizeStatisticDTO, String> column = new Column<SizeStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(SizeStatisticDTO dto) {
				return dto.getSize();
			}
		};
		return column;
	}

	private Column<SizeStatisticDTO, String> createPiecesColumn() {
		Column<SizeStatisticDTO, String> column = new Column<SizeStatisticDTO, String>(new TextCell()) {
			@Override
			public String getValue(SizeStatisticDTO dto) {
				return textMessages.pieces(dto.getPieces());
			}
		};
		column.setCellStyleNames("statisticValue");
		return column;
	}

}
