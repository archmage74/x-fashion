package com.xfashion.client.color;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.ChooseAttributePopup;
import com.xfashion.client.Xfashion;
import com.xfashion.shared.ColorDTO;

public class ChooseColorPopup extends ChooseAttributePopup<ColorDTO> {

	public ChooseColorPopup(ListDataProvider<ColorDTO> dataProvider) {
		super(dataProvider);
	}

	@Override
	protected void select(ColorDTO item) {
		Xfashion.eventBus.fireEvent(new ChooseColorEvent(item));
	}
}
