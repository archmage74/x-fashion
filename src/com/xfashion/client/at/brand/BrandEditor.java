package com.xfashion.client.at.brand;

import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.SimpleFilterPanel;
import com.xfashion.client.at.FilterEditor;
import com.xfashion.client.at.brand.event.CreateBrandEvent;
import com.xfashion.client.at.brand.event.DeleteBrandEvent;
import com.xfashion.client.at.brand.event.MoveDownBrandEvent;
import com.xfashion.client.at.brand.event.MoveUpBrandEvent;
import com.xfashion.client.at.brand.event.UpdateBrandEvent;
import com.xfashion.shared.BrandDTO;

public class BrandEditor extends FilterEditor<BrandDTO> {

	public BrandEditor(SimpleFilterPanel<BrandDTO> filterPanel, EventBus adminBus) {
		super(filterPanel, adminBus);
	}

	@Override
	protected void moveUp(BrandDTO dto, int index) {
		adminBus.fireEvent(new MoveUpBrandEvent(dto, index));
	}

	@Override
	protected void moveDown(BrandDTO dto, int index) {
		adminBus.fireEvent(new MoveDownBrandEvent(dto, index));
	}

	@Override
	protected void delete(BrandDTO dto) {
		adminBus.fireEvent(new DeleteBrandEvent(dto));
	}

	@Override
	protected void createDTO() {
		BrandDTO item = new BrandDTO();
		fillDTOFromPanel(item);
		adminBus.fireEvent(new CreateBrandEvent(item));
	}

	@Override
	public void updateDTO(BrandDTO brand) {
		adminBus.fireEvent(new UpdateBrandEvent(brand));
	}

}
