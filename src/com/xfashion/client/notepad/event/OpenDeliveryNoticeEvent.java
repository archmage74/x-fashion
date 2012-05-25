package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.DeliveryNoticeDTO;

public class OpenDeliveryNoticeEvent extends Event<OpenDeliveryNoticeHandler> {

	public static Type<OpenDeliveryNoticeHandler> TYPE = new Type<OpenDeliveryNoticeHandler>();
	
	protected DeliveryNoticeDTO deliveryNotice;

	public OpenDeliveryNoticeEvent(DeliveryNoticeDTO deliveryNotice) {
		this.deliveryNotice = deliveryNotice;
	}
	
	public DeliveryNoticeDTO getDeliveryNotice() {
		return deliveryNotice;
	}
	
	@Override
	public Type<OpenDeliveryNoticeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(OpenDeliveryNoticeHandler handler) {
		handler.onOpenDeliveryNotice(this);
	}

}
