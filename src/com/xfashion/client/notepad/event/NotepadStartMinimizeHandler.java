package com.xfashion.client.notepad.event;

import com.xfashion.client.FilterDataEventHandler;

public interface NotepadStartMinimizeHandler extends FilterDataEventHandler {
	
	void onNotepadStartMinimize(NotepadStartMinimizeEvent event);
	
}
