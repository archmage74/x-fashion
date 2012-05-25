package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;

public class PrintDeliveryNoticeEvent extends Event<PrintDeliveryNoticeHandler> {

	public static Type<PrintDeliveryNoticeHandler> TYPE = new Type<PrintDeliveryNoticeHandler>();
	
	@Override
	public Type<PrintDeliveryNoticeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PrintDeliveryNoticeHandler handler) {
		handler.onPrintDeliveryNotice(this);
	}

}
