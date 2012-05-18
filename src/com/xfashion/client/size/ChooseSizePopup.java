package com.xfashion.client.size;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.ChooseAttributePopup2;
import com.xfashion.client.Xfashion;
import com.xfashion.shared.SizeDTO;

public class ChooseSizePopup extends ChooseAttributePopup2<SizeDTO> {

	public ChooseSizePopup(ListDataProvider<SizeDTO> dataProvider) {
		super(dataProvider);
	}

	@Override
	protected void select(SizeDTO item) {
		Xfashion.eventBus.fireEvent(new ChooseSizeEvent(item));
	}
}
