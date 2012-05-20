package com.xfashion.client.notepad;

import com.google.web.bindery.event.shared.Event;

public class PrintNotepadStickersEvent extends Event<PrintNotepadStickersHandler> {

	public static Type<PrintNotepadStickersHandler> TYPE = new Type<PrintNotepadStickersHandler>();
	
	@Override
	public Type<PrintNotepadStickersHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PrintNotepadStickersHandler handler) {
		handler.onPrintNotepadStickers(this);
	}

}
