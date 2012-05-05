package com.xfashion.client.notepad;

import com.xfashion.client.FilterDataEventHandler;

public interface NotepadMaximizedHandler extends FilterDataEventHandler {
	
	void onNotepadMaximized(NotepadMaximizedEvent event);
	
}
