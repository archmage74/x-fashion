package com.xfashion.client.notepad;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.notepad.NotepadDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("notepadService")
public interface NotepadService extends RemoteService {
	
	public static final String SESSION_NOTEPAD = "currentNotepad";

	void saveNotepadInSession(NotepadDTO user);
	
}
