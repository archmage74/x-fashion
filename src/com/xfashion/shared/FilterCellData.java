package com.xfashion.shared;



public abstract class FilterCellData extends DTO {
	
	private String name;
	
	private Integer articleAmount;
	
	private Boolean hidden;
	
	public FilterCellData() {
		articleAmount = 0;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getArticleAmount() {
		return articleAmount;
	}

	public void setArticleAmount(Integer articleAmount) {
		this.articleAmount = articleAmount;
	}
	
	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((articleAmount == null) ? 0 : articleAmount.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		FilterCellData other = (FilterCellData) obj;
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
		return true;
	}

}
