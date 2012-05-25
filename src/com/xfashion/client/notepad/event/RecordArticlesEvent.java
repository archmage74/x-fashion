package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;

public class RecordArticlesEvent extends Event<RecordArticlesHandler> {

	public static Type<RecordArticlesHandler> TYPE = new Type<RecordArticlesHandler>();
	
	@Override
	public Type<RecordArticlesHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RecordArticlesHandler handler) {
		handler.onRecordArticles(this);
	}

}
