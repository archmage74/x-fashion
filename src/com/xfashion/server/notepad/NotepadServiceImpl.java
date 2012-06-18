package com.xfashion.server.notepad;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.notepad.NotepadService;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.NotepadDTO;

public class NotepadServiceImpl extends RemoteServiceServlet implements NotepadService {

//	private static final Logger log = Logger.getLogger(NotepadServiceImpl.class.getName());
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void saveNotepadInSession(NotepadDTO dto) {
		this.getThreadLocalRequest().getSession().setAttribute(SESSION_NOTEPAD, dto);
	}

	@Override
	public void saveDeliveryNoticeInSession(DeliveryNoticeDTO dto) {
		this.getThreadLocalRequest().getSession().setAttribute(SESSION_DELIVERY_NOTICE, dto);
	}
	
}
