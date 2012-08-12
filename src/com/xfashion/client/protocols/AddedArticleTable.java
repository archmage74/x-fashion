package com.xfashion.client.protocols;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Formatter;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.notepad.GetPriceFromArticleAmountStrategy;
import com.xfashion.shared.AddedArticleDTO;

public class AddedArticleTable extends ArticleTable<AddedArticleDTO> {

	protected Formatter formatter;
	
	protected IGetPriceStrategy<AddedArticleDTO> priceStrategy;

	public AddedArticleTable(IProvideArticleFilter provider, GetPriceFromArticleAmountStrategy<AddedArticleDTO> getPriceStrategy) {
		super(provider);
		this.formatter = Formatter.getInstance();
		this.priceStrategy = getPriceStrategy;
	}

	@Override
	protected IGetPriceStrategy<AddedArticleDTO> currentPriceStrategy() {
		return priceStrategy;
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
