package com.xfashion.client.event;

import com.google.web.bindery.event.shared.Event;

public class ContentPanelResizeEvent extends Event<ContentPanelResizeHandler> {
	
	public static Type<ContentPanelResizeHandler> TYPE = new Type<ContentPanelResizeHandler>();

	private int height;
	
	public ContentPanelResizeEvent(int height) {
		this.height = height;
	}
	
	public int getHeight() {
		return height;
	}
	
	@Override
	public Type<ContentPanelResizeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ContentPanelResizeHandler handler) {
		handler.onContentPanelResize(this);
	}

}
