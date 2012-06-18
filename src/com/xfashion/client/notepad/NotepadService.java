package com.xfashion.client.notepad;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.NotepadDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("notepadService")
public interface NotepadService extends RemoteService {
	
	public static final String SESSION_NOTEPAD = "currentNotepad";
	
	public static final String SESSION_DELIVERY_NOTICE = "currentDeliveryNotice";

	void saveNotepadInSession(NotepadDTO user);

	void saveDeliveryNoticeInSession(DeliveryNoticeDTO currentNotepad);
	
}
