package com.xfashion.client.removed;

import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Formatter;
import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.at.render.RemovedArticlePriceCell;
import com.xfashion.client.notepad.GetPriceFromArticleAmountStrategy;
import com.xfashion.shared.RemovedArticleDTO;

public class RemovedArticleTable extends ArticleTable<RemovedArticleDTO> {

	IGetPriceStrategy<RemovedArticleDTO> priceStrategy;
	
	protected Formatter formatter;

	public RemovedArticleTable(IProvideArticleFilter provider, GetPriceFromArticleAmountStrategy<RemovedArticleDTO> getPriceStrategy) {
		super(provider);
		this.formatter = Formatter.getInstance();
		this.priceStrategy = getPriceStrategy;
	}

	@Override
	protected IGetPriceStrategy<RemovedArticleDTO> currentPriceStrategy() {
		return priceStrategy;
	}

	@Override
	protected Column<RemovedArticleDTO, RemovedArticleDTO> createPriceColumn(ArticleDataProvider<RemovedArticleDTO> ap) {
		Column<RemovedArticleDTO, RemovedArticleDTO> price = new Column<RemovedArticleDTO, RemovedArticleDTO>(new RemovedArticlePriceCell(priceStrategy)) {
			@Override
			public RemovedArticleDTO getValue(RemovedArticleDTO a) {
				return a;
			}
		};
		return price;
	}

}
