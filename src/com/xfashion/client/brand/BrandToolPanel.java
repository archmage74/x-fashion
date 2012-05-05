package com.xfashion.client.brand;

import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.ToolPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.shared.BrandDTO;

public class BrandToolPanel extends ToolPanel<BrandDTO> {

	public BrandToolPanel(FilterPanel<BrandDTO> parentPanel, PanelMediator panelMediator) {
		super(parentPanel, panelMediator);
	}

	@Override
	protected void createDTOFromPanel() {
		BrandDTO brand = new BrandDTO();
		fillDTOFromPanel(brand);
		Xfashion.eventBus.fireEvent(new CreateBrandEvent(brand));
	}

}
