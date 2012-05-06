package com.xfashion.client.notepad;

import com.xfashion.client.FilterDataEventHandler;

public interface NotepadStartMinimizeHandler extends FilterDataEventHandler {
	
	void onNotepadStartMinimize(NotepadStartMinimizeEvent event);
	
}
