package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;

public class NotepadStartMinimizeEvent extends Event<NotepadStartMinimizeHandler> {
	
	public static Type<NotepadStartMinimizeHandler> TYPE = new Type<NotepadStartMinimizeHandler>();

	public NotepadStartMinimizeEvent() {

	}
	
	@Override
	public Type<NotepadStartMinimizeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NotepadStartMinimizeHandler handler) {
		handler.onNotepadStartMinimize(this);
	}

}
