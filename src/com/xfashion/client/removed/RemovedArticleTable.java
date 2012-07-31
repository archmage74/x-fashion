package com.xfashion.client.removed;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Formatter;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.notepad.GetPriceFromArticleAmountStrategy;
import com.xfashion.shared.RemovedArticleDTO;

public class RemovedArticleTable extends ArticleTable<RemovedArticleDTO> {

	protected Formatter formatter;

	public RemovedArticleTable(ProvidesArticleFilter provider, GetPriceFromArticleAmountStrategy<RemovedArticleDTO> getPriceStrategy) {
		super(provider, getPriceStrategy);
		this.formatter = Formatter.getInstance();
	}

	protected void addNavColumns(CellTable<RemovedArticleDTO> cellTable) {
		cellTable.addColumn(createAmountColumn());
		cellTable.addColumn(createAddedDateColumn());
	}

	private Column<RemovedArticleDTO, String> createAddedDateColumn() {
		Column<RemovedArticleDTO, String> amount = new Column<RemovedArticleDTO, String>(new TextCell()) {
			@Override
			public String getValue(RemovedArticleDTO sa) {
				return textMessages.addedToStockDate(sa.getRemoveDate());
			}
		};
		amount.setCellStyleNames("articleSellDate");
		return amount;
	}

	protected Column<RemovedArticleDTO, String> createAmountColumn() {
		Column<RemovedArticleDTO, String> amount = new Column<RemovedArticleDTO, String>(new TextCell()) {
			@Override
			public String getValue(RemovedArticleDTO a) {
				return a.getAmount().toString();
			}
		};
		amount.setCellStyleNames("articleAmount");
		return amount;
	}

}
