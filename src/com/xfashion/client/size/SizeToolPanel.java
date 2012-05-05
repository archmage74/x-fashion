package com.xfashion.client.size;

import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.ToolPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.shared.SizeDTO;

public class SizeToolPanel extends ToolPanel<SizeDTO> {

	public SizeToolPanel(FilterPanel<SizeDTO> parentPanel, PanelMediator panelMediator) {
		super(parentPanel, panelMediator);
	}

	@Override
	protected void createDTOFromPanel() {
		SizeDTO size = new SizeDTO();
		fillDTOFromPanel(size);
		Xfashion.eventBus.fireEvent(new CreateSizeEvent(size));
	}

}
