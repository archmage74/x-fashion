package com.xfashion.client.at.color;

import com.google.gwt.resources.client.ImageResource;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.ResizeableIconFilterPanel;
import com.xfashion.client.SimpleFilterDataProvider;
import com.xfashion.client.at.color.event.ClearColorSelectionEvent;
import com.xfashion.client.at.color.event.SelectColorEvent;
import com.xfashion.shared.ColorDTO;

public class ColorPanel extends ResizeableIconFilterPanel<ColorDTO> {

	public ColorPanel(SimpleFilterDataProvider<ColorDTO> dataProvider, EventBus eventBus) {
		super(dataProvider, eventBus);
	}

	@Override
	public void clearSelection() {
		eventBus.fireEvent(new ClearColorSelectionEvent());
	}

	@Override
	public String getPanelTitle() {
		return textMessages.color();
	}

	@Override
	public void select(ColorDTO dto) {
		eventBus.fireEvent(new SelectColorEvent(dto));
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
