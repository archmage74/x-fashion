package com.xfashion.shared;


public abstract class FilterCellData2 extends DTO2 {
	
	private String name;
	
	private boolean selected;
	
	private Integer articleAmount;
	
	public FilterCellData2() {
		this.selected = false;
		articleAmount = 0;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	public Integer getArticleAmount() {
		return articleAmount;
	}


	public void setArticleAmount(Integer articleAmount) {
		this.articleAmount = articleAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((articleAmount == null) ? 0 : articleAmount.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (selected ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FilterCellData2 other = (FilterCellData2) obj;
		if (articleAmount == null) {
			if (other.articleAmount != null)
				return false;
		} else if (!articleAmount.equals(other.articleAmount))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (selected != other.selected)
			return false;
		return true;
	}

}
