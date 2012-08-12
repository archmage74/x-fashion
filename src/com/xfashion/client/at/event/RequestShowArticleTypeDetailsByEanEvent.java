package com.xfashion.client.at.event;

import com.google.web.bindery.event.shared.Event;

public class RequestShowArticleTypeDetailsByEanEvent extends Event<RequestShowArticleTypeDetailsByEanHandler> {
	
	public static Type<RequestShowArticleTypeDetailsByEanHandler> TYPE = new Type<RequestShowArticleTypeDetailsByEanHandler>();

	protected long ean;

	public RequestShowArticleTypeDetailsByEanEvent(long ean) {
		this.ean = ean;
	}

	public long getEan() {
		return ean;
	}
	
	@Override
	public Type<RequestShowArticleTypeDetailsByEanHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RequestShowArticleTypeDetailsByEanHandler handler) {
		handler.onRequestShowArticleTypeDetailsByEan(this);
	}

}
