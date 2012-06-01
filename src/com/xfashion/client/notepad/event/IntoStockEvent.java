package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.notepad.NotepadDTO;

public class IntoStockEvent extends Event<IntoStockHandler> {

	public static Type<IntoStockHandler> TYPE = new Type<IntoStockHandler>();
	
	NotepadDTO notepad;

	public IntoStockEvent(NotepadDTO notepad) {
		this.notepad = notepad;
	}
	
	public NotepadDTO getNotepad() {
		return notepad;
	}

	@Override
	public Type<IntoStockHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(IntoStockHandler handler) {
		handler.onIntoStock(this);
	}

}
