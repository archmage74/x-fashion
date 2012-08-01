package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;

public class RequestCheckNotepadPositionEvent extends Event<RequestCheckNotepadPositionHandler> {

	public static Type<RequestCheckNotepadPositionHandler> TYPE = new Type<RequestCheckNotepadPositionHandler>();
	
	@Override
	public Type<RequestCheckNotepadPositionHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RequestCheckNotepadPositionHandler handler) {
		handler.onRequestCheckNotepadPosition(this);
	}

}
