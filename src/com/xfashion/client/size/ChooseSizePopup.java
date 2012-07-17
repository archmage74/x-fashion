package com.xfashion.client.size;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.ChooseAttributePopup;
import com.xfashion.client.Xfashion;
import com.xfashion.client.size.event.ChooseSizeEvent;
import com.xfashion.shared.SizeDTO;

public class ChooseSizePopup extends ChooseAttributePopup<SizeDTO> {

	public ChooseSizePopup(ListDataProvider<SizeDTO> dataProvider) {
		super(dataProvider);
	}

	@Override
	protected void select(SizeDTO item) {
		Xfashion.eventBus.fireEvent(new ChooseSizeEvent(item));
	}
}
