package com.xfashion.client.protocols;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Formatter;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.notepad.GetPriceFromArticleAmountStrategy;
import com.xfashion.shared.AddedArticleDTO;

public class AddedArticleTable extends ArticleTable<AddedArticleDTO> {

	protected Formatter formatter;

	public AddedArticleTable(ProvidesArticleFilter provider, GetPriceFromArticleAmountStrategy<AddedArticleDTO> getPriceStrategy) {
		super(provider, getPriceStrategy);
		this.formatter = Formatter.getInstance();
	}

	protected void addNavColumns(CellTable<AddedArticleDTO> cellTable) {
		cellTable.addColumn(createAmountColumn());
		cellTable.addColumn(createAddedDateColumn());
	}

	private Column<AddedArticleDTO, String> createAddedDateColumn() {
		Column<AddedArticleDTO, String> amount = new Column<AddedArticleDTO, String>(new TextCell()) {
			@Override
			public String getValue(AddedArticleDTO sa) {
				return textMessages.addedToStockDate(sa.getAddDate());
			}
		};
		amount.setCellStyleNames("articleSellDate");
		return amount;
	}

	protected Column<AddedArticleDTO, String> createAmountColumn() {
		Column<AddedArticleDTO, String> amount = new Column<AddedArticleDTO, String>(new TextCell()) {
			@Override
			public String getValue(AddedArticleDTO a) {
				return a.getAmount().toString();
			}
		};
		amount.setCellStyleNames("articleAmount");
		return amount;
	}

}
