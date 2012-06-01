package com.xfashion.client.stock;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.notepad.event.NotepadRemoveArticleEvent;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.notepad.ArticleAmountDTO;

public class StockArticleTable extends ArticleTable<ArticleAmountDTO> {

	public StockArticleTable(ProvidesArticleFilter provider) {
		super(provider);
	}

	protected void addNavColumns(CellTable<ArticleAmountDTO> cellTable) {
		Column<ArticleAmountDTO, String> amount = new Column<ArticleAmountDTO, String>(new TextCell()) {
			@Override
			public String getValue(ArticleAmountDTO a) {
				return a.getAmount().toString();
			}
		};
		amount.setCellStyleNames("articleAmount");
		cellTable.addColumn(amount);
	}
}
