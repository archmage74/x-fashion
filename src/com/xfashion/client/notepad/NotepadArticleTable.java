package com.xfashion.client.notepad;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.notepad.event.NotepadRemoveArticleEvent;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;

public class NotepadArticleTable extends ArticleTable<ArticleAmountDTO> {

	public NotepadArticleTable(ProvidesArticleFilter provider) {
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
				return textMessages.removeOneFromNotepadButton();
			}
		};
		cellTable.addColumn(notepadButton);
		notepadButton.setFieldUpdater(new FieldUpdater<ArticleAmountDTO, String>() {
			@Override
			public void update(int index, ArticleAmountDTO a, String value) {
				ArticleTypeDTO at = articleProvider.retrieveArticleType(a);
				Xfashion.eventBus.fireEvent(new NotepadRemoveArticleEvent(at));
			}
		});

		Column<ArticleAmountDTO, String> removeTenFromNotepadButton = new Column<ArticleAmountDTO, String>(new ButtonCell()) {
			@Override
			public String getValue(ArticleAmountDTO at) {
				return textMessages.removeTenFromNotepadButton();
			}
		};
		cellTable.addColumn(removeTenFromNotepadButton);
		removeTenFromNotepadButton.setFieldUpdater(new FieldUpdater<ArticleAmountDTO, String>() {
			@Override
			public void update(int index, ArticleAmountDTO a, String value) {
				ArticleTypeDTO at = articleProvider.retrieveArticleType(a);
				Xfashion.eventBus.fireEvent(new NotepadRemoveArticleEvent(at, 10));
			}
		});

	}
}
