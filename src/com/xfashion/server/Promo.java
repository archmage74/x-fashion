package com.xfashion.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.PromoDTO;

@PersistenceCapable
public class Promo {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Long ean;

	@Persistent
	private Integer price;
	
	@Persistent
	private Integer percent;
	
	@Persistent
	private Boolean activated;
	
	public Promo() {
		
	}
	
	public Promo(PromoDTO dto) {
		updateFromDTO(dto);
	}
	
	public void updateFromDTO(PromoDTO dto) {
		this.ean = dto.getEan();
		this.price = dto.getPrice();
		this.percent = dto.getPercent();
		this.activated = dto.isActivated();
	}
	
	public PromoDTO createDTO() {
		PromoDTO dto = new PromoDTO();
		dto.setKey(getKeyString());
		dto.setEan(ean);
		dto.setPrice(price);
		dto.setPercent(percent);
		dto.setActivated(activated);
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

	public Long getEan() {
		return ean;
	}

	public void setEan(Long ean) {
		this.ean = ean;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}
	
	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

}
