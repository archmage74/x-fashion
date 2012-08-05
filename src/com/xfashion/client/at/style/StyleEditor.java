package com.xfashion.client.at.style;

import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.SimpleFilterPanel;
import com.xfashion.client.at.FilterEditor;
import com.xfashion.client.at.style.event.CreateStyleEvent;
import com.xfashion.client.at.style.event.DeleteStyleEvent;
import com.xfashion.client.at.style.event.MoveDownStyleEvent;
import com.xfashion.client.at.style.event.MoveUpStyleEvent;
import com.xfashion.client.at.style.event.UpdateStyleEvent;
import com.xfashion.shared.StyleDTO;

public class StyleEditor extends FilterEditor<StyleDTO> {

	public StyleEditor(SimpleFilterPanel<StyleDTO> filterPanel, EventBus adminBus) {
		super(filterPanel, adminBus);
	}

	@Override
	protected void moveUp(StyleDTO dto, int index) {
		adminBus.fireEvent(new MoveUpStyleEvent(dto, index));
	}

	@Override
	protected void moveDown(StyleDTO dto, int index) {
		adminBus.fireEvent(new MoveDownStyleEvent(dto, index));
	}

	@Override
	protected void delete(StyleDTO dto) {
		adminBus.fireEvent(new DeleteStyleEvent(dto));
	}

	@Override
	protected void createDTO() {
		StyleDTO item = new StyleDTO();
		fillDTOFromPanel(item);
		adminBus.fireEvent(new CreateStyleEvent(item));
	}

	@Override
	public void updateDTO(StyleDTO style) {
		adminBus.fireEvent(new UpdateStyleEvent(style));
	}

}
