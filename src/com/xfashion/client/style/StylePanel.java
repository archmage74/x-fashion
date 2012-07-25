package com.xfashion.client.style;

import com.google.gwt.resources.client.ImageResource;
import com.xfashion.client.ErrorEvent;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.ResizeableIconFilterPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.style.event.ClearStyleSelectionEvent;
import com.xfashion.client.style.event.CreateStyleEvent;
import com.xfashion.client.style.event.DeleteStyleEvent;
import com.xfashion.client.style.event.MoveDownStyleEvent;
import com.xfashion.client.style.event.MoveUpStyleEvent;
import com.xfashion.client.style.event.SelectStyleEvent;
import com.xfashion.client.style.event.UpdateStyleEvent;
import com.xfashion.shared.StyleDTO;

public class StylePanel extends ResizeableIconFilterPanel<StyleDTO> {

	public StylePanel(FilterDataProvider<StyleDTO> dataProvider) {
		super(dataProvider);
	}

	@Override
	public void clearSelection() {
		Xfashion.eventBus.fireEvent(new ClearStyleSelectionEvent());
	}

	@Override
	public String getPanelTitle() {
		return textMessages.style();
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
	public void delete(StyleDTO style) {
		if (style.getArticleAmount() != null && style.getArticleAmount() > 0) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.sizeIsNotEmpty(style.getName())));
		} else {
			Xfashion.eventBus.fireEvent(new DeleteStyleEvent(style));
		}
	}

	@Override
	protected void select(StyleDTO dto) {
		Xfashion.eventBus.fireEvent(new SelectStyleEvent(dto));
	}

	@Override
	protected void createDTO() {
		StyleDTO item = new StyleDTO();
		fillDTOFromPanel(item);
		Xfashion.eventBus.fireEvent(new CreateStyleEvent(item));
	}

	@Override
	protected ImageResource getSelectedIcon() {
		return images.iconStyleSelected();
	}

	@Override
	protected ImageResource getAvailableIcon() {
		return images.iconStyleUnselected();
	}

	@Override
	public void updateDTO(StyleDTO item) {
		Xfashion.eventBus.fireEvent(new UpdateStyleEvent(item));
	}

}
