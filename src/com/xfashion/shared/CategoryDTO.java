package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CategoryDTO extends DTO implements IsSerializable, Comparable<CategoryDTO> {

	public static final String[][] COLOR_SCHEMAS = { { "#76A573", "#466944" }, { "#6F77AA", "#2B3781" }, { "#8AADB8", "#578C9B" }, { "#9F7F79", "#71433A" },
			{ "#9EC1AA", "#74A886" }, { "#A26E7B", "#7C3044" }, { "#AD89B8", "#89559A" }, { "#B2B7D9", "#919BCA" }, { "#C1AEA5", "#A78C7E" },
			{ "#CEAFD0", "#B98DBC" }, { "#D7A899", "#C5846E" } };


	
	private Long id;
	
	private String name;
	
	private String borderColor;
	
	private String backgroundColor;
	
	private boolean inEditMode = false;

	private int sortIndex = -1;
	
	public CategoryDTO() {
		
	}

	public CategoryDTO(String name, String backgroundColor, String borderColor, int sortIndex) {
		this.name = name;
		this.backgroundColor = backgroundColor;
		this.borderColor = borderColor;
		this.sortIndex = sortIndex;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof CategoryDTO)) return false;
		CategoryDTO s = (CategoryDTO) o;
		boolean equal = true;
		equal &= attributeEquals(name, s.getName());
		equal &= attributeEquals(borderColor, s.getBorderColor());
		equal &= attributeEquals(backgroundColor, s.getBackgroundColor());
		equal &= attributeEquals(sortIndex, s.getSortIndex());
		return equal;
	}

	@Override
	public int compareTo(CategoryDTO o) {
		int cmp = sortIndex - o.getSortIndex();
		if (cmp == 0) {
			long cmp2 = id - o.getId();
			return (int) (cmp2 / Math.abs(cmp2)); // convert to +1 or -1
		} else {
			return cmp;
		}
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

	public boolean isInEditMode() {
		return inEditMode;
	}

	public void setInEditMode(boolean inEditMode) {
		this.inEditMode = inEditMode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

}
