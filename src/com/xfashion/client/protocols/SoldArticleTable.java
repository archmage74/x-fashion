package com.xfashion.client.protocols;

import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Formatter;
import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.at.price.GetSellPriceFromSoldArticleStrategy;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.at.render.SoldArticlePriceCell;
import com.xfashion.shared.SoldArticleDTO;

public class SoldArticleTable extends ArticleTable<SoldArticleDTO> {

	protected Formatter formatter;

	protected IGetPriceStrategy<SoldArticleDTO> priceStrategy;

	public SoldArticleTable(IProvideArticleFilter provider, GetSellPriceFromSoldArticleStrategy getPriceStrategy) {
		super(provider);
		this.formatter = Formatter.getInstance();
		this.priceStrategy = getPriceStrategy;
	}

	@Override
	protected IGetPriceStrategy<SoldArticleDTO> currentPriceStrategy() {
		return priceStrategy;
	}

	@Override
	protected Column<SoldArticleDTO, SoldArticleDTO> createPriceColumn(ArticleDataProvider<SoldArticleDTO> ap) {
		Column<SoldArticleDTO, SoldArticleDTO> price = new Column<SoldArticleDTO, SoldArticleDTO>(new SoldArticlePriceCell(priceStrategy)) {
			@Override
			public SoldArticleDTO getValue(SoldArticleDTO a) {
				return a;
			}
		};
		return price;
	}

	@Override
	protected String resolveCategory(SoldArticleDTO a) {
		String s = a.getCategory();
		if (s == null) {
			s = textMessages.unknownCategory();
		}
		return s;
	}

	@Override
	protected String resolveStyle(SoldArticleDTO a) {
		String s = a.getStyle();
		if (s == null) {
			s = textMessages.unknownStyle();
		}
		return s;
	}

	@Override
	protected String resolveBrand(SoldArticleDTO a) {
		String s = a.getBrand();
		if (s == null) {
			s = textMessages.unknownBrand();
		}
		return s;
	}

	@Override
	protected String resolveName(SoldArticleDTO a) {
		String s = a.getArticleName();
		if (s == null) {
			s = textMessages.unknownName();
		}
		return s;

	}

	@Override
	protected String resolveColor(SoldArticleDTO a) {
		String s = a.getColor();
		if (s == null) {
			s = textMessages.unknownColor();
		}
		return s;
	}

	@Override
	protected String resolveSize(SoldArticleDTO a) {
		String s = a.getSize();
		if (s == null) {
			s = textMessages.unknownSize();
		}
		return s;
	}
	
}
