package com.xfashion.client.notepad;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.client.Xfashion;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.NotepadDTO;

public class NotepadPrinter {

	public static final String PRINT_STICKER_URL = "/pdf/multisticker";

	public static final String PRINT_DELIVERY_NOTICE_URL = "/pdf/deliverynotice";

	protected NotepadServiceAsync notepadService;

	public NotepadPrinter() {
		this.notepadService = (NotepadServiceAsync) GWT.create(NotepadService.class);
	}
	
	public void printNotepad(NotepadDTO notepad) {
		AsyncCallback<Void> callback = createPrintCallback(PRINT_STICKER_URL);
		sendNotepadPrintAction(notepad, callback);
	}
	
	public void printDeliveryNotice(DeliveryNoticeDTO deliveryNotice) {
		AsyncCallback<Void> callback = createPrintCallback(PRINT_DELIVERY_NOTICE_URL);
		sendDeliveryNoticePrintAction(deliveryNotice, callback);
	}
	
	protected void sendNotepadPrintAction(NotepadDTO notepad, AsyncCallback<Void> printCallback) {
		notepadService.saveNotepadInSession(notepad, printCallback);
	}
	
	protected void sendDeliveryNoticePrintAction(DeliveryNoticeDTO deliveryNotice, AsyncCallback<Void> printCallback) {
		notepadService.saveDeliveryNoticeInSession(deliveryNotice, printCallback);
	}

	protected AsyncCallback<Void> createPrintCallback(final String url) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				Window.open(url, "_blank", "");
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		return callback;
	}

}
