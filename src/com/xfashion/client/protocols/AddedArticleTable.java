package com.xfashion.client.protocols;

import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Formatter;
import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.at.render.AddedArticlePriceCell;
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

	@Override
	protected Column<AddedArticleDTO, AddedArticleDTO> createPriceColumn(ArticleDataProvider<AddedArticleDTO> ap) {
		Column<AddedArticleDTO, AddedArticleDTO> price = new Column<AddedArticleDTO, AddedArticleDTO>(new AddedArticlePriceCell(priceStrategy)) {
			@Override
			public AddedArticleDTO getValue(AddedArticleDTO a) {
				return a;
			}
		};
		return price;
	}

}
