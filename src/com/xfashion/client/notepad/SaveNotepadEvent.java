package com.xfashion.client.notepad;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.notepad.NotepadDTO;

public class SaveNotepadEvent extends Event<SaveNotepadHandler> {

	public static Type<SaveNotepadHandler> TYPE = new Type<SaveNotepadHandler>();
	
	protected NotepadDTO notepad;
	
	public SaveNotepadEvent(NotepadDTO notepad) {
		this.notepad = notepad;
	}
	
	public NotepadDTO getNotepad() {
		return notepad;
	}
	
	@Override
	public Type<SaveNotepadHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SaveNotepadHandler handler) {
		handler.onSaveNotepad(this);
	}

}
