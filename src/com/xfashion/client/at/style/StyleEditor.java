package com.xfashion.client.at.style;

import com.xfashion.client.SimpleFilterPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.FilterEditor;
import com.xfashion.client.at.style.event.CreateStyleEvent;
import com.xfashion.client.at.style.event.DeleteStyleEvent;
import com.xfashion.client.at.style.event.MoveDownStyleEvent;
import com.xfashion.client.at.style.event.MoveUpStyleEvent;
import com.xfashion.client.at.style.event.UpdateStyleEvent;
import com.xfashion.shared.StyleDTO;

public class StyleEditor extends FilterEditor<StyleDTO> {

	public StyleEditor(SimpleFilterPanel<StyleDTO> filterPanel) {
		super(filterPanel);
	}

	@Override
	protected void moveUp(StyleDTO dto, int index) {
		Xfashion.eventBus.fireEvent(new MoveUpStyleEvent(dto, index));
	}

	@Override
	protected void moveDown(StyleDTO dto, int index) {
		Xfashion.eventBus.fireEvent(new MoveDownStyleEvent(dto, index));
	}

	@Override
	protected void delete(StyleDTO dto) {
		Xfashion.eventBus.fireEvent(new DeleteStyleEvent(dto));
	}

	@Override
	protected void createDTO() {
		StyleDTO item = new StyleDTO();
		fillDTOFromPanel(item);
		Xfashion.eventBus.fireEvent(new CreateStyleEvent(item));
	}

	@Override
	public void updateDTO(StyleDTO style) {
		Xfashion.eventBus.fireEvent(new UpdateStyleEvent(style));
	}

}
