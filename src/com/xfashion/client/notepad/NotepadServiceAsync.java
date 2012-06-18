package com.xfashion.client.notepad;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.NotepadDTO;

public interface NotepadServiceAsync {

	void saveNotepadInSession(NotepadDTO notepad, AsyncCallback<Void> callback);

	void saveDeliveryNoticeInSession(DeliveryNoticeDTO currentNotepad, AsyncCallback<Void> printCallback);
	
}
