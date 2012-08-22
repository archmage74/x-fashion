package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;

public class HideNotepadEvent extends Event<HideNotepadHandler> {

	public static Type<HideNotepadHandler> TYPE = new Type<HideNotepadHandler>();
	
	@Override
	public Type<HideNotepadHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(HideNotepadHandler handler) {
		handler.onHideNotepad(this);
	}

}
