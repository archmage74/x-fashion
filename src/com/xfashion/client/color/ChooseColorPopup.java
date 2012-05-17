package com.xfashion.client.color;

import com.xfashion.client.ChooseAttributePopup2;
import com.xfashion.client.FilterPanel2;
import com.xfashion.client.PanelMediator;
import com.xfashion.shared.ColorDTO;

public class ChooseColorPopup extends ChooseAttributePopup2<ColorDTO> {

	public ChooseColorPopup(PanelMediator panelMediator) {
		super(panelMediator);
	}

	@Override
	public FilterPanel2<ColorDTO> getPanel() {
		return null;
//		return panelMediator.getColorPanel();
	}

}
