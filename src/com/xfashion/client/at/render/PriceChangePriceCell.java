package com.xfashion.client.at.render;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.pricechange.PriceChangeArticleAmountDataProvider;
import com.xfashion.client.pricechange.PriceChangeMatrixTemplates;
import com.xfashion.client.pricechange.event.PrintChangePriceStickersEvent;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.PriceChangeDTO;

public class PriceChangePriceCell extends AbstractArticleTableCell<ArticleAmountDTO> {

	protected PriceChangeArticleAmountDataProvider priceChangeProvider;

	protected IGetPriceStrategy<ArticleAmountDTO> priceStrategy;
	
	protected PriceChangeMatrixTemplates priceChangeTemplates = GWT.create(PriceChangeMatrixTemplates.class);
	
	public PriceChangePriceCell(PriceChangeArticleAmountDataProvider provider, IGetPriceStrategy<ArticleAmountDTO> getPriceStrategy) {
		super("click");
		this.priceChangeProvider = provider;
		this.priceStrategy = getPriceStrategy;
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, ArticleAmountDTO value, NativeEvent event, ValueUpdater<ArticleAmountDTO> valueUpdater) {
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
		if ("click".equals(event.getType())) {
			EventTarget eventTarget = event.getEventTarget();
			if (!Element.is(eventTarget)) {
				return;
			}
			if (parent.getChild(2).isOrHasChild(Element.as(eventTarget))) {
				Xfashion.eventBus.fireEvent(new PrintChangePriceStickersEvent(value));
			} 
		}
	}

	@Override
	public void render(Context context, ArticleAmountDTO data, SafeHtmlBuilder sb) {
		if (isPriceChangeAccepted(data)) {
			sb.append(matrixTemplates.piecesCell(data.getAmount()));
			String priceString = formatter.formatCentsToCurrencyOrUnknown(priceStrategy.getPrice(data));
			sb.append(matrixTemplates.priceCell(priceString));
			sb.append(priceChangeTemplates.printStickerCell());
		} else {
			sb.append(matrixTemplates.piecesHighlightedCell(data.getAmount()));
			String priceString = formatter.formatCentsToCurrencyOrUnknown(priceStrategy.getPrice(data));
			sb.append(matrixTemplates.priceHighlightedCell(priceString));
			sb.append(priceChangeTemplates.printStickerHighlightedCell());
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
