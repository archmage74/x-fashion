package com.xfashion.client.notepad;

import com.google.web.bindery.event.shared.Event;

public class NotepadStartMaximizeEvent extends Event<NotepadStartMaximizeHandler> {
	
	public static Type<NotepadStartMaximizeHandler> TYPE = new Type<NotepadStartMaximizeHandler>();

	public NotepadStartMaximizeEvent() {

	}
	
	@Override
	public Type<NotepadStartMaximizeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NotepadStartMaximizeHandler handler) {
		handler.onNotepadStartMaximize(this);
	}

}
