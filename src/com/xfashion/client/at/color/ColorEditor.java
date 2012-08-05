package com.xfashion.client.at.color;

import com.xfashion.client.SimpleFilterPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.FilterEditor;
import com.xfashion.client.at.color.event.CreateColorEvent;
import com.xfashion.client.at.color.event.DeleteColorEvent;
import com.xfashion.client.at.color.event.MoveDownColorEvent;
import com.xfashion.client.at.color.event.MoveUpColorEvent;
import com.xfashion.client.at.color.event.UpdateColorEvent;
import com.xfashion.shared.ColorDTO;

public class ColorEditor extends FilterEditor<ColorDTO> {

	public ColorEditor(SimpleFilterPanel<ColorDTO> filterPanel) {
		super(filterPanel);
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
	protected void createDTO() {
		ColorDTO item = new ColorDTO();
		fillDTOFromPanel(item);
		Xfashion.eventBus.fireEvent(new CreateColorEvent(item));
	}

	@Override
	public void updateDTO(ColorDTO color) {
		Xfashion.eventBus.fireEvent(new UpdateColorEvent(color));
	}

}
