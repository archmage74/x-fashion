package com.xfashion.client.cat;

import com.xfashion.client.FilterCellData;
import com.xfashion.shared.CategoryDTO;


public class CategoryCellData extends FilterCellData {

	public static String ICON_PREFIX_CATEGORY = "category";
	
	private CategoryDTO categoryDTO;
	
	public CategoryDTO getCategoryDTO() {
		return categoryDTO;
	}

	@Override
	public String getName() {
		if (categoryDTO != null) {
			return categoryDTO.getName();
		} else {
			return null;
		}
	}

	@Override
	public void setName(String name) {
		categoryDTO.setName(name);
	}

	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}
	
	public CategoryCellData(CategoryDTO categoryDTO) {
		setCategoryDTO(categoryDTO);
		setName(categoryDTO.getName());
		setAvailable(true);
		setIconPrefix(ICON_PREFIX_CATEGORY);
	}
	
}
