package com.xfashion.client.notepad.action;

import java.util.Comparator;

import com.xfashion.shared.DeliveryNoticeDTO;

public class DeliveryNoticeById implements Comparator<DeliveryNoticeDTO> {

	@Override
	public int compare(DeliveryNoticeDTO arg0, DeliveryNoticeDTO arg1) {
		return arg0.getId().compareTo(arg1.getId());
	}

}
