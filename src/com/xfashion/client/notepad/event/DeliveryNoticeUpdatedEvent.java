package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.DeliveryNoticeDTO;

public class DeliveryNoticeUpdatedEvent extends Event<DeliveryNoticeUpdatedHandler> {

	public static Type<DeliveryNoticeUpdatedHandler> TYPE = new Type<DeliveryNoticeUpdatedHandler>();
	
	protected DeliveryNoticeDTO deliveryNotice;

	public DeliveryNoticeUpdatedEvent(DeliveryNoticeDTO deliveryNotice) {
		this.deliveryNotice = deliveryNotice;
	}
	
	public DeliveryNoticeDTO getDeliveryNotice() {
		return deliveryNotice;
	}
	
	@Override
	public Type<DeliveryNoticeUpdatedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeliveryNoticeUpdatedHandler handler) {
		handler.onDeliveryNoticeUpdated(this);
	}

}
