package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.NotepadDTO;

public class NotepadUpdatedEvent extends Event<NotepadUpdatedHandler> {

	public static Type<NotepadUpdatedHandler> TYPE = new Type<NotepadUpdatedHandler>();
	
	protected NotepadDTO notepad;
	
	public NotepadUpdatedEvent(NotepadDTO notepad) {
		this.notepad = notepad;
	}
	
	public NotepadDTO getNotepad() {
		return notepad;
	}
	
	@Override
	public Type<NotepadUpdatedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NotepadUpdatedHandler handler) {
		handler.onNotepadUpdated(this);
	}

}
