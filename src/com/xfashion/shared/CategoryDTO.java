package com.xfashion.shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CategoryDTO extends FilterCellData2 implements IsSerializable {

	private List<StyleDTO> styles;
	
	public CategoryDTO() {
		super();
		styles = new ArrayList<StyleDTO>();
	}
	
	public CategoryDTO(String name) {
		this();
		setName(name);
	}

	public List<StyleDTO> getStyles() {
		return styles;
	}

	public void setStyles(List<StyleDTO> styles) {
		this.styles = styles;
	}

}
