package com.xfashion.client.pricechange;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.IGetPriceStrategy;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.pricechange.event.PrintChangePriceStickersEvent;
import com.xfashion.shared.ArticleAmountDTO;

public class PriceChangeArticleTable extends ArticleTable<ArticleAmountDTO> {

	public PriceChangeArticleTable(ProvidesArticleFilter provider, IGetPriceStrategy<ArticleAmountDTO> getPriceStrategy) {
		super(provider, getPriceStrategy);
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
				return textMessages.printChangePriceStickers();
			}
		};
		cellTable.addColumn(notepadButton);
		notepadButton.setFieldUpdater(new FieldUpdater<ArticleAmountDTO, String>() {
			@Override
			public void update(int index, ArticleAmountDTO am, String value) {
				Xfashion.eventBus.fireEvent(new PrintChangePriceStickersEvent(am));
			}
		});

	}
	
	@Override
	protected String getAdditionalStyles(ArticleAmountDTO am) {
		return "priceChangeAccepted";
	}
}
