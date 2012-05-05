package com.xfashion.client.color;

import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.ToolPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.shared.ColorDTO;

public class ColorToolPanel extends ToolPanel<ColorDTO> {

	public ColorToolPanel(FilterPanel<ColorDTO> parentPanel, PanelMediator panelMediator) {
		super(parentPanel, panelMediator);
	}

	@Override
	protected void createDTOFromPanel() {
		ColorDTO color = new ColorDTO();
		fillDTOFromPanel(color);
		Xfashion.eventBus.fireEvent(new CreateColorEvent(color));
	}

}
