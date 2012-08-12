package com.xfashion.client.pricechange;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.pricechange.event.PrintChangePriceStickersEvent;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.PriceChangeDTO;

public class PriceChangeArticleTable extends ArticleTable<ArticleAmountDTO> {

	protected PriceChangeArticleAmountDataProvider priceChangeProvider;
	
	protected IGetPriceStrategy<ArticleAmountDTO> priceStrategy;
	
	public PriceChangeArticleTable(IProvideArticleFilter provider, IGetPriceStrategy<ArticleAmountDTO> priceStrategy,
			PriceChangeArticleAmountDataProvider priceChangeProvider) {
		super(provider);
		this.priceChangeProvider = priceChangeProvider;
		this.priceStrategy = priceStrategy;
	}

	@Override
	protected IGetPriceStrategy<ArticleAmountDTO> currentPriceStrategy() {
		return priceStrategy;
	}
	
	protected void addNavColumns(CellTable<ArticleAmountDTO> cellTable) {
		Column<ArticleAmountDTO, SafeHtml> amount = new Column<ArticleAmountDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(ArticleAmountDTO a) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				String styles = concatStyles("articleAmount", getAdditionalPriceStyles(a));
				sb.appendHtmlConstant("<div class=\"" + styles + "\">");
				sb.appendEscaped(a.getAmount().toString());
				sb.appendHtmlConstant("</div>");
				return sb.toSafeHtml();
			}
		};
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
	protected String getAdditionalMatrixStyles(ArticleAmountDTO am) {
		if (isPriceChangeAccepted(am)) {
			return null;
		} else {
			return "priceChangeNotAcceptedMatrix";
		}
	}

	@Override
	protected String getAdditionalPriceStyles(ArticleAmountDTO am) {
		if (isPriceChangeAccepted(am)) {
			return null;
		} else {
			return "priceChangeNotAcceptedColumn";
		}
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
