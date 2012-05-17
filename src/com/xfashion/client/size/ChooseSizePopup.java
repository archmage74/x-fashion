package com.xfashion.client.size;

import com.xfashion.client.ChooseAttributePopup2;
import com.xfashion.client.FilterPanel2;
import com.xfashion.client.PanelMediator;
import com.xfashion.shared.SizeDTO;

public class ChooseSizePopup extends ChooseAttributePopup2<SizeDTO> {

	public ChooseSizePopup(PanelMediator panelMediator) {
		super(panelMediator);
	}

	@Override
	public FilterPanel2<SizeDTO> getPanel() {
		return null;
//		return panelMediator.getSizePanel();
	}

}
