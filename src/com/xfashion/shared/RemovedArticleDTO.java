package com.xfashion.shared;

import java.util.Date;

public class RemovedArticleDTO extends ArticleAmountDTO {

	private static final long serialVersionUID = 8130078401724950984L;

	protected Date removeDate;

	protected String shopKey;

	public RemovedArticleDTO() {

	}
	
	public RemovedArticleDTO(String articleTypeKey, Integer amount) {
		super(articleTypeKey, amount);
	}
	
	public Date getRemoveDate() {
		return removeDate;
	}

	public void setRemoveDate(Date removeDate) {
		this.removeDate = removeDate;
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
		result = prime * result + ((removeDate == null) ? 0 : removeDate.hashCode());
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
		RemovedArticleDTO other = (RemovedArticleDTO) obj;
		if (removeDate == null) {
			if (other.removeDate != null)
				return false;
		} else if (!removeDate.equals(other.removeDate))
			return false;
		if (shopKey == null) {
			if (other.shopKey != null)
				return false;
		} else if (!shopKey.equals(other.shopKey))
			return false;
		return true;
	}

}
