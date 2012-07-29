package com.xfashion.shared;

import java.util.Date;

public class AddedArticleDTO extends ArticleAmountDTO {

	private static final long serialVersionUID = 8130078401724950984L;

	protected Date addDate;

	protected String shopKey;

	public AddedArticleDTO() {

	}
	
	public AddedArticleDTO(String articleTypeKey, Integer amount) {
		super(articleTypeKey, amount);
	}
	
	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((addDate == null) ? 0 : addDate.hashCode());
		result = prime * result + ((shopKey == null) ? 0 : shopKey.hashCode());
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
		AddedArticleDTO other = (AddedArticleDTO) obj;
		if (addDate == null) {
			if (other.addDate != null)
				return false;
		} else if (!addDate.equals(other.addDate))
			return false;
		if (shopKey == null) {
			if (other.shopKey != null)
				return false;
		} else if (!shopKey.equals(other.shopKey))
			return false;
		return true;
	}

}
