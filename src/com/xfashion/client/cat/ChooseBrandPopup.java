package com.xfashion.client.cat;

import com.xfashion.client.ChooseAttributePopup;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.shared.BrandDTO;

public class ChooseBrandPopup extends ChooseAttributePopup<BrandDTO> {

	public ChooseBrandPopup(PanelMediator panelMediator) {
		super(panelMediator);
	}

	@Override
	public FilterPanel<BrandDTO> getPanel() {
		return panelMediator.getBrandPanel();
	}

}
