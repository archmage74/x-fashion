package com.xfashion.client.brand;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.ChooseAttributePopup2;
import com.xfashion.client.Xfashion;
import com.xfashion.shared.BrandDTO;

public class ChooseBrandPopup extends ChooseAttributePopup2<BrandDTO> {

	public ChooseBrandPopup(ListDataProvider<BrandDTO> dataProvider) {
		super(dataProvider);
	}

	@Override
	protected void select(BrandDTO item) {
		Xfashion.eventBus.fireEvent(new ChooseBrandEvent(item));
	}
}
