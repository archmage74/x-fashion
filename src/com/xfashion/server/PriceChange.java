package com.xfashion.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.PriceChangeDTO;

@PersistenceCapable
public class PriceChange {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	protected Key articleTypeKey;

	@Persistent
	protected Date changeDate;
	
	@Persistent
	private Integer sellPriceAt;
	
	@Persistent
	private Integer sellPriceDe;
	
	@Persistent
	private Boolean accepted;

	public PriceChange() {
		this.changeDate = new Date();
	}
	
	public PriceChange(PriceChangeDTO dto) {
		this.articleTypeKey = KeyFactory.stringToKey(dto.getArticleTypeKey());
		this.sellPriceAt = dto.getSellPriceAt();
		this.sellPriceDe = dto.getSellPriceDe();
		if (dto.getChangeDate() == null) {
			this.changeDate = new Date();
		} else {
			this.changeDate = dto.getChangeDate();
		}
		this.accepted = dto.getAccepted();
	}
	
	public void updateFromDTO(PriceChangeDTO dto) {
		// TODO validate if nothing else than accepted has changed
		this.accepted = dto.getAccepted();
	}
	

	public PriceChangeDTO createDTO() {
		PriceChangeDTO dto = new PriceChangeDTO();
		dto.setKeyString(getKeyString());
		dto.setArticleTypeKey(getArticleTypeKeyAsString());
		dto.setSellPriceAt(sellPriceAt);
		dto.setSellPriceDe(sellPriceDe);
		dto.setChangeDate(changeDate);
		dto.setAccepted(accepted);
		return dto;
	}
	
	public Key getKey() {
		return key;
	}
	
	public String getKeyString() {
		return KeyFactory.keyToString(key);
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getArticleTypeKey() {
		return articleTypeKey;
	}

	public void setArticleTypeKey(Key articleTypeKey) {
		this.articleTypeKey = articleTypeKey;
	}

	public String getArticleTypeKeyAsString() {
		return KeyFactory.keyToString(articleTypeKey);
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

}
