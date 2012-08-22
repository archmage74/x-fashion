package com.xfashion.client.at;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.at.render.ArticleTypePriceCell;
import com.xfashion.client.notepad.event.NotepadAddArticleEvent;
import com.xfashion.shared.ArticleTypeDTO;

public class ArticleTypeTable extends ArticleTable<ArticleTypeDTO> {

	IGetPriceStrategy<ArticleTypeDTO> priceStratgey;

	public ArticleTypeTable(IProvideArticleFilter provider, IGetPriceStrategy<ArticleTypeDTO> getPriceStrategy) {
		super(provider);
		this.priceStratgey = getPriceStrategy;
	}

	@Override
	protected IGetPriceStrategy<ArticleTypeDTO> currentPriceStrategy() {
		return priceStratgey;
	}

	protected Column<ArticleTypeDTO, ArticleTypeDTO> createPriceColumn(final ArticleDataProvider<ArticleTypeDTO> ap) {
		Column<ArticleTypeDTO, ArticleTypeDTO> price = new Column<ArticleTypeDTO, ArticleTypeDTO>(
				new ArticleTypePriceCell<ArticleTypeDTO>(articleProvider)) {
			@Override
			public ArticleTypeDTO getValue(ArticleTypeDTO a) {
				return a;
			}
		};
		return price;
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
