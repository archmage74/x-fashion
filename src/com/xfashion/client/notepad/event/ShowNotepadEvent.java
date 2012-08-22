package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;

public class ShowNotepadEvent extends Event<ShowNotepadHandler> {

	public static Type<ShowNotepadHandler> TYPE = new Type<ShowNotepadHandler>();
	
	@Override
	public Type<ShowNotepadHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowNotepadHandler handler) {
		handler.onShowNotepad(this);
	}

}
