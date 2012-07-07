package com.xfashion.client.stock;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.notepad.event.NotepadAddArticleEvent;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;

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

		Column<ArticleAmountDTO, String> notepadButton = new Column<ArticleAmountDTO, String>(new ButtonCell()) {
			@Override
			public String getValue(ArticleAmountDTO at) {
				return textMessages.addOneToNotepadButton();
			}
		};
		cellTable.addColumn(notepadButton);
		notepadButton.setFieldUpdater(new FieldUpdater<ArticleAmountDTO, String>() {
			@Override
			public void update(int index, ArticleAmountDTO am, String value) {
				ArticleTypeDTO at = articleProvider.retrieveArticleType(am);
				Xfashion.eventBus.fireEvent(new NotepadAddArticleEvent(at));
			}
		});

		Column<ArticleAmountDTO, String> tenToNotepadButton = new Column<ArticleAmountDTO, String>(new ButtonCell()) {
			@Override
			public String getValue(ArticleAmountDTO at) {
				return textMessages.addTenToNotepadButton();
			}
		};
		cellTable.addColumn(tenToNotepadButton);
		tenToNotepadButton.setFieldUpdater(new FieldUpdater<ArticleAmountDTO, String>() {
			@Override
			public void update(int index, ArticleAmountDTO am, String value) {
				ArticleTypeDTO at = articleProvider.retrieveArticleType(am);
				Xfashion.eventBus.fireEvent(new NotepadAddArticleEvent(at, 10));
			}
		});

	}
}
