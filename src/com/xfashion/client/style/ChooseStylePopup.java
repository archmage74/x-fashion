package com.xfashion.client.style;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.ChooseAttributePopup2;
import com.xfashion.shared.StyleDTO;

public class ChooseStylePopup extends ChooseAttributePopup2<StyleDTO> {

	public ChooseStylePopup(ListDataProvider<StyleDTO> dataProvider) {
		super(dataProvider);
	}

	@Override
	protected void select(StyleDTO item) {
		// TODO Auto-generated method stub
		
	}
}
