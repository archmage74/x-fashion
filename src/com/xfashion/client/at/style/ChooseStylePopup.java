package com.xfashion.client.at.style;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.ChooseAttributePopup;
import com.xfashion.shared.StyleDTO;

public class ChooseStylePopup extends ChooseAttributePopup<StyleDTO> {

	public ChooseStylePopup(ListDataProvider<StyleDTO> dataProvider) {
		super(dataProvider);
	}

	@Override
	protected void select(StyleDTO item) {
		// TODO Auto-generated method stub
		
	}
}
