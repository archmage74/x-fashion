package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CategoryDTO extends FilterCellData implements IsSerializable {

	public static final String[][] COLOR_SCHEMAS = { { "#76A573", "#466944" }, { "#6F77AA", "#2B3781" }, { "#8AADB8", "#578C9B" }, { "#9F7F79", "#71433A" },
			{ "#9EC1AA", "#74A886" }, { "#A26E7B", "#7C3044" }, { "#AD89B8", "#89559A" }, { "#B2B7D9", "#919BCA" }, { "#C1AEA5", "#A78C7E" },
			{ "#CEAFD0", "#B98DBC" }, { "#D7A899", "#C5846E" } };

	public static String ICON_PREFIX_CATEGORY = "category";

	private String borderColor;
	
	private String backgroundColor;
	
	public CategoryDTO() {
		
	}

	public CategoryDTO(Long id, String name, int sortIndex, String backgroundColor, String borderColor) {
		setId(id);
		setName(name);
		setSortIndex(sortIndex);
		setBackgroundColor(backgroundColor);
		setBorderColor(borderColor);
		setIconPrefix(ICON_PREFIX_CATEGORY);
	}

	@Override
	public int getHeight() {
		return 39;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof CategoryDTO)) return false;
		CategoryDTO s = (CategoryDTO) o;
		boolean equal = true;
		equal &= attributeEquals(getName(), s.getName());
		equal &= attributeEquals(getSortIndex(), s.getSortIndex());
		equal &= attributeEquals(getBorderColor(), s.getBorderColor());
		equal &= attributeEquals(getBackgroundColor(), s.getBackgroundColor());
		return equal;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
