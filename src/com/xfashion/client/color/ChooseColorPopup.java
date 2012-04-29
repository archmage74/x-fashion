package com.xfashion.client.color;

import com.xfashion.client.ChooseAttributePopup;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.shared.ColorDTO;

public class ChooseColorPopup extends ChooseAttributePopup<ColorDTO> {

	public ChooseColorPopup(PanelMediator panelMediator) {
		super(panelMediator);
	}

	@Override
	public FilterPanel<ColorDTO> getPanel() {
		return panelMediator.getColorPanel();
	}

}
