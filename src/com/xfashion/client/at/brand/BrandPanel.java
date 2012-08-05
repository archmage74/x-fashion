package com.xfashion.client.at.brand;

import com.google.gwt.resources.client.ImageResource;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.ResizeableIconFilterPanel;
import com.xfashion.client.SimpleFilterDataProvider;
import com.xfashion.client.at.brand.event.ClearBrandSelectionEvent;
import com.xfashion.client.at.brand.event.SelectBrandEvent;
import com.xfashion.shared.BrandDTO;

public class BrandPanel extends ResizeableIconFilterPanel<BrandDTO> {

	public BrandPanel(SimpleFilterDataProvider<BrandDTO> dataProvider, EventBus eventBus) {
		super(dataProvider, eventBus);
	}
	
	@Override
	public void clearSelection() {
		eventBus.fireEvent(new ClearBrandSelectionEvent());
	}

	@Override
	public String getPanelTitle() {
		return textMessages.brand();
	}

	@Override
	public void select(BrandDTO dto) {
		eventBus.fireEvent(new SelectBrandEvent(dto));
	}

	@Override
	protected ImageResource getSelectedIcon() {
		return images.iconColorSelected();
	}

	@Override
	protected ImageResource getAvailableIcon() {
		return images.iconColorUnselected();
	}

}
