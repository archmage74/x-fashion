package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.DeliveryNoticeDTO;

public class SaveDeliveryNoticeEvent extends Event<SaveDeliveryNoticeHandler> {

	public static Type<SaveDeliveryNoticeHandler> TYPE = new Type<SaveDeliveryNoticeHandler>();
	
	protected DeliveryNoticeDTO deliveryNotice;
	
	public SaveDeliveryNoticeEvent(DeliveryNoticeDTO deliveryNotice) {
		this.deliveryNotice = deliveryNotice;
	}
	
	public DeliveryNoticeDTO getDeliveryNotice() {
		return deliveryNotice;
	}
	
	@Override
	public Type<SaveDeliveryNoticeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SaveDeliveryNoticeHandler handler) {
		handler.onSaveDeliveryNotice(this);
	}

}
