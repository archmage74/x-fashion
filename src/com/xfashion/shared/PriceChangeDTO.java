package com.xfashion.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PriceChangeDTO implements IsSerializable {

	private String keyString;
	
	private String articleTypeKey;
	
	private Date changeDate;

	private Integer sellPriceAt;
	
	private Integer sellPriceDe;
	
	private Boolean accepted;

	public String getKeyString() {
		return keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

	public String getArticleTypeKey() {
		return articleTypeKey;
	}
	
	public void setArticleTypeKey(String articleTypeKey) {
		this.articleTypeKey = articleTypeKey;
	}
	
	public Date getChangeDate() {
		return changeDate;
	}
	
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	
	public Integer getSellPriceAt() {
		return sellPriceAt;
	}

	public void setSellPriceAt(Integer sellPriceAt) {
		this.sellPriceAt = sellPriceAt;
	}

	public Integer getSellPriceDe() {
		return sellPriceDe;
	}

	public void setSellPriceDe(Integer sellPriceDe) {
		this.sellPriceDe = sellPriceDe;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public PriceChangeDTO clone() {
		PriceChangeDTO clone = new PriceChangeDTO();
		clone.setArticleTypeKey(articleTypeKey);
		clone.setChangeDate(changeDate);
		clone.setSellPriceAt(sellPriceAt);
		clone.setSellPriceDe(sellPriceDe);
		clone.setAccepted(accepted);
		return clone;
	}
}
