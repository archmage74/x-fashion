package com.xfashion.client.notepad;

import com.google.web.bindery.event.shared.Event;

public class RequestSaveNotepadEvent extends Event<RequestSaveNotepadHandler> {

	public static Type<RequestSaveNotepadHandler> TYPE = new Type<RequestSaveNotepadHandler>();
	
	@Override
	public Type<RequestSaveNotepadHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RequestSaveNotepadHandler handler) {
		handler.onRequestSaveNotepad(this);
	}

}
