package com.xfashion.client.style;

import com.xfashion.client.ChooseAttributePopup;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.shared.StyleDTO;

public class ChooseStylePopup extends ChooseAttributePopup<StyleDTO> {

	public ChooseStylePopup(PanelMediator panelMediator) {
		super(panelMediator);
	}

	@Override
	public FilterPanel<StyleDTO> getPanel() {
		return panelMediator.getStylePanel();
	}

}
