package com.xfashion.client.cat;

import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.FilterCellData;


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
		setIconPrefix(ICON_PREFIX_CATEGORY);
	}

	@Override
	public int getHeight() {
		return 39;
	}

}
