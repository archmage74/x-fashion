package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;

public class RequestOpenNotepadEvent extends Event<RequestOpenNotepadHandler> {

	public static Type<RequestOpenNotepadHandler> TYPE = new Type<RequestOpenNotepadHandler>();
	
	@Override
	public Type<RequestOpenNotepadHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RequestOpenNotepadHandler handler) {
		handler.onRequestOpenNotepad(this);
	}

}
