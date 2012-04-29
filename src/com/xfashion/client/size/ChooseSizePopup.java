package com.xfashion.client.size;

import com.xfashion.client.ChooseAttributePopup;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.shared.SizeDTO;

public class ChooseSizePopup extends ChooseAttributePopup<SizeDTO> {

	public ChooseSizePopup(PanelMediator panelMediator) {
		super(panelMediator);
	}

	@Override
	public FilterPanel<SizeDTO> getPanel() {
		return panelMediator.getSizePanel();
	}

}
