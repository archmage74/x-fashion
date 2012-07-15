package com.xfashion.client.at;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Xfashion;
import com.xfashion.client.notepad.event.NotepadAddArticleEvent;
import com.xfashion.shared.ArticleTypeDTO;

public class ArticleTypeTable extends ArticleTable<ArticleTypeDTO> {

	public ArticleTypeTable(ProvidesArticleFilter provider, IGetPriceStrategy<ArticleTypeDTO> getPriceStrategy) {
		super(provider, getPriceStrategy);
	}

	protected Column<ArticleTypeDTO, SafeHtml> createPriceColumn(final ArticleDataProvider<ArticleTypeDTO> ap) {
		Column<ArticleTypeDTO, SafeHtml> price = new Column<ArticleTypeDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(ArticleTypeDTO a) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant("<div class=\"articleTypePrice\">");
				Integer priceAt = a.getSellPriceAt();
				if (priceAt != null) {
					sb.appendEscaped(formatter.formatCentsToCurrency(priceAt));
				} else {
					sb.appendEscaped(textMessages.unknownPrice());
				}
				sb.appendEscaped(" [AT]");
				sb.appendHtmlConstant("</div>");
				sb.appendHtmlConstant("<div class=\"articleTypePrice\">");
				Integer priceDe = a.getSellPriceDe();
				if (priceDe != null) {
					sb.appendEscaped(formatter.formatCentsToCurrency(priceDe));
				} else {
					sb.appendEscaped(textMessages.unknownPrice());
				}
				sb.appendEscaped(" [DE]");
				sb.appendHtmlConstant("</div>");
				return sb.toSafeHtml();
			}
		};
		price.setCellStyleNames("articlePrice");
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
