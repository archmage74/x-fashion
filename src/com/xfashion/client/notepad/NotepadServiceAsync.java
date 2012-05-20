package com.xfashion.client.notepad;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.notepad.NotepadDTO;

public interface NotepadServiceAsync {

	void saveNotepadInSession(NotepadDTO notepad, AsyncCallback<Void> callback);
	
}
