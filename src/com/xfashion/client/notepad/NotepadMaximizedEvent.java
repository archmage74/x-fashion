package com.xfashion.client.notepad;

import com.google.web.bindery.event.shared.Event;

public class NotepadMaximizedEvent extends Event<NotepadMaximizedHandler> {
	
	public static Type<NotepadMaximizedHandler> TYPE = new Type<NotepadMaximizedHandler>();

	public NotepadMaximizedEvent() {

	}
	
	@Override
	public Type<NotepadMaximizedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NotepadMaximizedHandler handler) {
		handler.onNotepadMaximized(this);
	}

}
