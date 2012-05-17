package com.xfashion.client.brand;

import com.google.gwt.resources.client.ImageResource;
import com.xfashion.client.SimpleFilterDataProvider2;
import com.xfashion.client.SimpleFilterPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.shared.BrandDTO;

public class BrandPanel extends SimpleFilterPanel<BrandDTO> {

	public BrandPanel(SimpleFilterDataProvider2<BrandDTO> dataProvider) {
		super(dataProvider);
	}
	
	@Override
	public void clearSelection() {
		Xfashion.eventBus.fireEvent(new ClearBrandSelectionEvent());
	}

	@Override
	public String getPanelTitle() {
		return textMessages.brand();
	}

	@Override
	protected void moveUp(BrandDTO dto, int index) {
		Xfashion.eventBus.fireEvent(new MoveUpBrandEvent(dto, index));
	}

	@Override
	protected void moveDown(BrandDTO dto, int index) {
		Xfashion.eventBus.fireEvent(new MoveDownBrandEvent(dto, index));
	}

	@Override
	protected void delete(BrandDTO dto) {
		Xfashion.eventBus.fireEvent(new DeleteBrandEvent(dto));
	}

	@Override
	protected void select(BrandDTO dto) {
		Xfashion.eventBus.fireEvent(new SelectBrandEvent(dto));
	}

	@Override
	protected void createDTO() {
		BrandDTO item = new BrandDTO();
		fillDTOFromPanel(item);
		Xfashion.eventBus.fireEvent(new CreateBrandEvent(item));
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
	public void updateDTO(BrandDTO brand) {
		Xfashion.eventBus.fireEvent(new UpdateBrandEvent(brand));
	}

}
