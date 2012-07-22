package com.xfashion.client.pricechange.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleAmountDTO;

public class PrintChangePriceStickersEvent extends Event<PrintChangePriceStickersHandler> {

	public static Type<PrintChangePriceStickersHandler> TYPE = new Type<PrintChangePriceStickersHandler>();
	
	private ArticleAmountDTO articleAmount;
	
	public PrintChangePriceStickersEvent(ArticleAmountDTO articleAmount) {
		this.articleAmount = articleAmount;
	}
	
	public ArticleAmountDTO getArticleAmount() {
		return articleAmount;
	}

	@Override
	public Type<PrintChangePriceStickersHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PrintChangePriceStickersHandler handler) {
		handler.onPrintChangePriceStickers(this);
	}

}
