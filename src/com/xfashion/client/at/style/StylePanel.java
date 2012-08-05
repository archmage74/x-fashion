package com.xfashion.client.at.style;

import com.google.gwt.resources.client.ImageResource;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.ResizeableIconFilterPanel;
import com.xfashion.client.at.style.event.ClearStyleSelectionEvent;
import com.xfashion.client.at.style.event.SelectStyleEvent;
import com.xfashion.shared.StyleDTO;

public class StylePanel extends ResizeableIconFilterPanel<StyleDTO> {

	public StylePanel(FilterDataProvider<StyleDTO> dataProvider, EventBus eventBus) {
		super(dataProvider, eventBus);
	}

	@Override
	public void clearSelection() {
		eventBus.fireEvent(new ClearStyleSelectionEvent());
	}

	@Override
	public String getPanelTitle() {
		return textMessages.style();
	}

	@Override
	public void select(StyleDTO dto) {
		eventBus.fireEvent(new SelectStyleEvent(dto));
	}

	@Override
	protected ImageResource getSelectedIcon() {
		return images.iconStyleSelected();
	}

	@Override
	protected ImageResource getAvailableIcon() {
		return images.iconStyleUnselected();
	}

}
