package com.xfashion.shared;


public abstract class FilterCellData extends DTO implements Comparable<FilterCellData>{
	
	private String name;
	
	private Integer sortIndex;
	
	private boolean selected;
	
	private Integer articleAmount;
	
	private String iconPrefix;
	
	private boolean inEditMode;
	
	public FilterCellData() {
		this.selected = false;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}
	
	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	public String getIconPrefix() {
		return iconPrefix;
	}

	public void setIconPrefix(String iconPrefix) {
		this.iconPrefix = iconPrefix;
	}

	public Integer getArticleAmount() {
		return articleAmount;
	}

	public void setArticleAmount(Integer articleAmount) {
		this.articleAmount = articleAmount;
	}

	public int getHeight() {
		return 20;
	}

	public boolean isInEditMode() {
		return inEditMode;
	}

	public void setInEditMode(boolean inEditMode) {
		this.inEditMode = inEditMode;
	}

	@Override
	public int compareTo(FilterCellData o) {
		int cmp = sortIndex - o.getSortIndex();
		if (cmp == 0) {
			long cmp2 = id - o.getId();
			return (int) (cmp2 / Math.abs(cmp2)); // convert to +1 or -1
		} else {
			return cmp;
		}
	}

}
