package com.xfashion.client.at;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Xfashion;
import com.xfashion.client.notepad.event.NotepadAddArticleEvent;
import com.xfashion.shared.ArticleTypeDTO;

public class ArticleTypeTable extends ArticleTable<ArticleTypeDTO> {

	public ArticleTypeTable(ProvidesArticleFilter provider) {
		super(provider);
	}

	protected void addNavColumns(CellTable<ArticleTypeDTO> cellTable) {
		Column<ArticleTypeDTO, String> notepadButton = new Column<ArticleTypeDTO, String>(new ButtonCell()) {
			@Override
			public String getValue(ArticleTypeDTO at) {
				return textMessages.addOneToNotepadButton();
			}
		};
		cellTable.addColumn(notepadButton);
		notepadButton.setFieldUpdater(new FieldUpdater<ArticleTypeDTO, String>() {
			@Override
			public void update(int index, ArticleTypeDTO at, String value) {
				Xfashion.eventBus.fireEvent(new NotepadAddArticleEvent(at));
			}
		});

		Column<ArticleTypeDTO, String> tenToNotepadButton = new Column<ArticleTypeDTO, String>(new ButtonCell()) {
			@Override
			public String getValue(ArticleTypeDTO at) {
				return textMessages.addTenToNotepadButton();
			}
		};
		cellTable.addColumn(tenToNotepadButton);
		tenToNotepadButton.setFieldUpdater(new FieldUpdater<ArticleTypeDTO, String>() {
			@Override
			public void update(int index, ArticleTypeDTO at, String value) {
				Xfashion.eventBus.fireEvent(new NotepadAddArticleEvent(at, 10));
			}
		});
	}
}
