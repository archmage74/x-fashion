package com.xfashion.client.brand;

import com.xfashion.client.ChooseAttributePopup2;
import com.xfashion.client.FilterPanel2;
import com.xfashion.client.PanelMediator;
import com.xfashion.shared.BrandDTO;

public class ChooseBrandPopup extends ChooseAttributePopup2<BrandDTO> {

	public ChooseBrandPopup(PanelMediator panelMediator) {
		super(panelMediator);
	}

	@Override
	public FilterPanel2<BrandDTO> getPanel() {
		return null;
//		return panelMediator.getBrandPanel();
	}

}
