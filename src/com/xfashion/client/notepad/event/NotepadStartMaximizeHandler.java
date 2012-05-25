package com.xfashion.client.notepad.event;

import com.xfashion.client.FilterDataEventHandler;

public interface NotepadStartMaximizeHandler extends FilterDataEventHandler {
	
	void onNotepadStartMaximize(NotepadStartMaximizeEvent event);
	
}
