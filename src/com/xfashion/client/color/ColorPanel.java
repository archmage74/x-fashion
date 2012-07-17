package com.xfashion.client.color;

import com.google.gwt.resources.client.ImageResource;
import com.xfashion.client.ResizeableIconFilterPanel;
import com.xfashion.client.SimpleFilterDataProvider;
import com.xfashion.client.Xfashion;
import com.xfashion.client.color.event.ClearColorSelectionEvent;
import com.xfashion.client.color.event.CreateColorEvent;
import com.xfashion.client.color.event.DeleteColorEvent;
import com.xfashion.client.color.event.MoveDownColorEvent;
import com.xfashion.client.color.event.MoveUpColorEvent;
import com.xfashion.client.color.event.SelectColorEvent;
import com.xfashion.client.color.event.UpdateColorEvent;
import com.xfashion.shared.ColorDTO;

public class ColorPanel extends ResizeableIconFilterPanel<ColorDTO> {

	public ColorPanel(SimpleFilterDataProvider<ColorDTO> dataProvider) {
		super(dataProvider);
	}

	@Override
	public void clearSelection() {
		Xfashion.eventBus.fireEvent(new ClearColorSelectionEvent());
	}

	@Override
	public String getPanelTitle() {
		return textMessages.color();
	}

	@Override
	protected void moveUp(ColorDTO dto, int index) {
		Xfashion.eventBus.fireEvent(new MoveUpColorEvent(dto, index));
	}

	@Override
	protected void moveDown(ColorDTO dto, int index) {
		Xfashion.eventBus.fireEvent(new MoveDownColorEvent(dto, index));
	}

	@Override
	protected void delete(ColorDTO dto) {
		Xfashion.eventBus.fireEvent(new DeleteColorEvent(dto));
	}

	@Override
	protected void select(ColorDTO dto) {
		Xfashion.eventBus.fireEvent(new SelectColorEvent(dto));
	}

	@Override
	protected void createDTO() {
		ColorDTO item = new ColorDTO();
		fillDTOFromPanel(item);
		Xfashion.eventBus.fireEvent(new CreateColorEvent(item));
	}

	@Override
	protected ImageResource getSelectedIcon() {
		return images.iconColorSelected();
	}

	@Override
	protected ImageResource getAvailableIcon() {
		return images.iconColorUnselected();
	}

	@Override
	public void updateDTO(ColorDTO color) {
		Xfashion.eventBus.fireEvent(new UpdateColorEvent(color));
	}

}
