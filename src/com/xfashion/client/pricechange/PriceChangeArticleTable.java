package com.xfashion.client.pricechange;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.at.render.PriceChangePriceCell;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.PriceChangeDTO;

public class PriceChangeArticleTable extends ArticleTable<ArticleAmountDTO> {

	protected PriceChangeArticleAmountDataProvider priceChangeProvider;

	protected IGetPriceStrategy<ArticleAmountDTO> priceStrategy;

	protected PriceChangeMatrixTemplates priceChangeTemplates;

	public PriceChangeArticleTable(IProvideArticleFilter provider, IGetPriceStrategy<ArticleAmountDTO> priceStrategy,
			PriceChangeArticleAmountDataProvider priceChangeProvider) {
		super(provider);
		this.priceChangeProvider = priceChangeProvider;
		this.priceStrategy = priceStrategy;
		this.priceChangeTemplates = GWT.create(PriceChangeMatrixTemplates.class);
	}

	@Override
	protected IGetPriceStrategy<ArticleAmountDTO> currentPriceStrategy() {
		return priceStrategy;
	}

	protected Column<ArticleAmountDTO, SafeHtml> createCBSColumn(final ArticleDataProvider<ArticleAmountDTO> ap) {
		Column<ArticleAmountDTO, SafeHtml> colorSize = new Column<ArticleAmountDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(ArticleAmountDTO a) {
				String category = resolveCategory(a);
				String brand = resolveBrand(a);
				String style = resolveStyle(a);
				if (isPriceChangeAccepted(a)) {
					return matrixTemplates.cbsColumn(category, brand, style);
				} else {
					return matrixTemplates.cbsHighlightedColumn(category, brand, style);
				}
			}
		};
		return colorSize;
	}
	
	@Override
	protected Column<ArticleAmountDTO, SafeHtml> createNCSColumn(final ArticleDataProvider<ArticleAmountDTO> ap) {
		Column<ArticleAmountDTO, SafeHtml> colorSize = new Column<ArticleAmountDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(ArticleAmountDTO a) {
				String name = resolveName(a);
				String color = resolveColor(a);
				String size = resolveSize(a);
				if (isPriceChangeAccepted(a)) {
					return matrixTemplates.ncsColumn(name, color, size);
				} else {
					return matrixTemplates.ncsHighlightedColumn(name, color, size);
				}
			}
		};
		return colorSize;
	}

	@Override
	protected Column<ArticleAmountDTO, ArticleAmountDTO> createPriceColumn(ArticleDataProvider<ArticleAmountDTO> ap) {
		Column<ArticleAmountDTO, ArticleAmountDTO> price = new Column<ArticleAmountDTO, ArticleAmountDTO>(new PriceChangePriceCell(
				priceChangeProvider, priceStrategy)) {
			@Override
			public ArticleAmountDTO getValue(ArticleAmountDTO a) {
				return a;
			}
		};
		return price;
	}

	private boolean isPriceChangeAccepted(ArticleAmountDTO am) {
		for (PriceChangeDTO priceChange : priceChangeProvider.getPriceChanges()) {
			if (!priceChange.getAccepted() && priceChange.getArticleTypeKey().equals(am.getArticleTypeKey())) {
				return false;
			}
		}
		return true;
	}

}
