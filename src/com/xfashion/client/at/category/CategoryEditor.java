package com.xfashion.client.at.category;

import com.xfashion.client.SimpleFilterPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.FilterEditor;
import com.xfashion.client.at.category.event.CreateCategoryEvent;
import com.xfashion.client.at.category.event.DeleteCategoryEvent;
import com.xfashion.client.at.category.event.MoveDownCategoryEvent;
import com.xfashion.client.at.category.event.MoveUpCategoryEvent;
import com.xfashion.client.at.category.event.UpdateCategoryEvent;
import com.xfashion.shared.CategoryDTO;

public class CategoryEditor extends FilterEditor<CategoryDTO> {

	public CategoryEditor(SimpleFilterPanel<CategoryDTO> filterPanel) {
		super(filterPanel);
	}

	@Override
	protected void moveUp(CategoryDTO dto, int index) {
		Xfashion.eventBus.fireEvent(new MoveUpCategoryEvent(dto, index));
	}

	@Override
	protected void moveDown(CategoryDTO dto, int index) {
		Xfashion.eventBus.fireEvent(new MoveDownCategoryEvent(dto, index));
	}

	@Override
	protected void delete(CategoryDTO dto) {
		Xfashion.eventBus.fireEvent(new DeleteCategoryEvent(dto));
	}

	@Override
	protected void createDTO() {
		CategoryDTO item = new CategoryDTO();
		fillDTOFromPanel(item);
		Xfashion.eventBus.fireEvent(new CreateCategoryEvent(item));
	}

	@Override
	public void updateDTO(CategoryDTO category) {
		Xfashion.eventBus.fireEvent(new UpdateCategoryEvent(category));
	}

}
