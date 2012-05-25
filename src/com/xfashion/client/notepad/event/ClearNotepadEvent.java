package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;

public class ClearNotepadEvent extends Event<ClearNotepadHandler> {

	public static Type<ClearNotepadHandler> TYPE = new Type<ClearNotepadHandler>();
	
	@Override
	public Type<ClearNotepadHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ClearNotepadHandler handler) {
		handler.onClearNotepad(this);
	}

}
