package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.notepad.NotepadDTO;

public class OpenNotepadEvent extends Event<OpenNotepadHandler> {

	public static Type<OpenNotepadHandler> TYPE = new Type<OpenNotepadHandler>();
	
	protected NotepadDTO notepad;
	
	public OpenNotepadEvent(NotepadDTO notepad) {
		this.notepad = notepad;
	}
	
	public NotepadDTO getNotepad() {
		return notepad;
	}
	
	@Override
	public Type<OpenNotepadHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(OpenNotepadHandler handler) {
		handler.onOpenNotepad(this);
	}

}
