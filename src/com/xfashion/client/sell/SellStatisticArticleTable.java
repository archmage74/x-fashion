package com.xfashion.client.sell;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Formatter;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.GetSellPriceStrategy;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.shared.SoldArticleDTO;

public class SellStatisticArticleTable extends ArticleTable<SoldArticleDTO> {

	protected Formatter formatter;

	public SellStatisticArticleTable(ProvidesArticleFilter provider, GetSellPriceStrategy getPriceStrategy) {
		super(provider, getPriceStrategy);
		formatter = new Formatter();
	}

	protected void addNavColumns(CellTable<SoldArticleDTO> cellTable) {
		Column<SoldArticleDTO, String> amount = new Column<SoldArticleDTO, String>(new TextCell()) {
			@Override
			public String getValue(SoldArticleDTO sa) {
				return textMessages.sellStatisticDate(sa.getSellDate());
			}
		};
		amount.setCellStyleNames("articleSellDate");
		cellTable.addColumn(amount);
	}

}
