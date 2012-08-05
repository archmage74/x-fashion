package com.xfashion.client.at.color;

import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.SimpleFilterPanel;
import com.xfashion.client.at.FilterEditor;
import com.xfashion.client.at.color.event.CreateColorEvent;
import com.xfashion.client.at.color.event.DeleteColorEvent;
import com.xfashion.client.at.color.event.MoveDownColorEvent;
import com.xfashion.client.at.color.event.MoveUpColorEvent;
import com.xfashion.client.at.color.event.UpdateColorEvent;
import com.xfashion.shared.ColorDTO;

public class ColorEditor extends FilterEditor<ColorDTO> {

	public ColorEditor(SimpleFilterPanel<ColorDTO> filterPanel, EventBus adminBus) {
		super(filterPanel, adminBus);
	}

	@Override
	protected void moveUp(ColorDTO dto, int index) {
		adminBus.fireEvent(new MoveUpColorEvent(dto, index));
	}

	@Override
	protected void moveDown(ColorDTO dto, int index) {
		adminBus.fireEvent(new MoveDownColorEvent(dto, index));
	}

	@Override
	protected void delete(ColorDTO dto) {
		adminBus.fireEvent(new DeleteColorEvent(dto));
	}

	@Override
	protected void createDTO() {
		ColorDTO item = new ColorDTO();
		fillDTOFromPanel(item);
		adminBus.fireEvent(new CreateColorEvent(item));
	}

	@Override
	public void updateDTO(ColorDTO color) {
		adminBus.fireEvent(new UpdateColorEvent(color));
	}

}
