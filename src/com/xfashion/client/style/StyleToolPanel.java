package com.xfashion.client.style;

import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.ToolPanel;
import com.xfashion.shared.StyleDTO;

public class StyleToolPanel extends ToolPanel<StyleDTO> {

	public StyleToolPanel(FilterPanel<StyleDTO> parentPanel, PanelMediator panelMediator) {
		super(parentPanel, panelMediator);
	}

	@Override
	protected void createDTOFromPanel() {
		StyleDTO style = new StyleDTO();
		fillDTOFromPanel(style);
		panelMediator.createStyle(style);
	}

}
