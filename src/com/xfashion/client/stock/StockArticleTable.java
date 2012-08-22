package com.xfashion.client.stock;

import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.at.render.StockPriceCell;
import com.xfashion.shared.ArticleAmountDTO;

public class StockArticleTable extends ArticleTable<ArticleAmountDTO> {

	IGetPriceStrategy<ArticleAmountDTO> priceStrategy;
	
	public StockArticleTable(IProvideArticleFilter provider, IGetPriceStrategy<ArticleAmountDTO> getPriceStrategy) {
		super(provider);
		this.priceStrategy = getPriceStrategy;
	}

	@Override
	protected IGetPriceStrategy<ArticleAmountDTO> currentPriceStrategy() {
		return priceStrategy;
	}

	@Override
	protected Column<ArticleAmountDTO, ArticleAmountDTO> createPriceColumn(ArticleDataProvider<ArticleAmountDTO> ap) {
		Column<ArticleAmountDTO, ArticleAmountDTO> price = new Column<ArticleAmountDTO, ArticleAmountDTO>(new StockPriceCell(ap, priceStrategy)) {
			@Override
			public ArticleAmountDTO getValue(ArticleAmountDTO a) {
				return a;
			}
		};
		return price;
	}
	
}
