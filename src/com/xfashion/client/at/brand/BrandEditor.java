package com.xfashion.client.at.brand;

import com.xfashion.client.SimpleFilterPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.FilterEditor;
import com.xfashion.client.at.brand.event.CreateBrandEvent;
import com.xfashion.client.at.brand.event.DeleteBrandEvent;
import com.xfashion.client.at.brand.event.MoveDownBrandEvent;
import com.xfashion.client.at.brand.event.MoveUpBrandEvent;
import com.xfashion.client.at.brand.event.UpdateBrandEvent;
import com.xfashion.shared.BrandDTO;

public class BrandEditor extends FilterEditor<BrandDTO> {

	public BrandEditor(SimpleFilterPanel<BrandDTO> filterPanel) {
		super(filterPanel);
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
	protected void createDTO() {
		BrandDTO item = new BrandDTO();
		fillDTOFromPanel(item);
		Xfashion.eventBus.fireEvent(new CreateBrandEvent(item));
	}

	@Override
	public void updateDTO(BrandDTO brand) {
		Xfashion.eventBus.fireEvent(new UpdateBrandEvent(brand));
	}

}
