package com.xfashion.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.xfashion.shared.ArticleTypeDTO;

@PersistenceCapable
public class ArticleType implements IsSerializable {
	
	public static final String ID_COUNTER_NAME = "ArticleType";
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long productNumber;

	@Persistent
	private String name;
	
	@Persistent
	private String categoryKey;
	
	@Persistent
	private Key styleKey;
	
	@Persistent
	private Key brandKey;
	
	@Persistent
	private Key sizeKey;
	
	@Persistent
	private Key colorKey;
	
	@Persistent
	private Integer buyPrice;
	
	@Persistent
	private Integer sellPrice;
	
	@Persistent
	private Key imageKey;

	public ArticleType() {
		
	}
	
	public ArticleType(ArticleTypeDTO dto) {
		updateFromDTO(dto);
	}
	
	public void updateFromDTO(ArticleTypeDTO dto) {
		setName(dto.getName());
		setCategoryKey(dto.getCategoryKey());
		setStyleId(KeyFactory.stringToKey(dto.getStyleKey()));
		setBrandKey(KeyFactory.stringToKey(dto.getBrandKey()));
		setSizeKey(KeyFactory.stringToKey(dto.getSizeKey()));
		setColorKey(KeyFactory.stringToKey(dto.getColorKey()));
		setBuyPrice(dto.getBuyPrice());
		setSellPrice(dto.getSellPrice());
		setImageKey(KeyFactory.stringToKey(dto.getImageKey()));
	}
	
	public ArticleTypeDTO createDTO() {
		ArticleTypeDTO dto = new ArticleTypeDTO();
		dto.setKey(getKeyString());
		dto.setProductNumber(getProductNumber());
		dto.setName(getName());
		dto.setCategoryKey(getCategoryKey());
		dto.setStyleKey(KeyFactory.keyToString(getStyleKey()));
		dto.setBrandKey(KeyFactory.keyToString(getBrandKey()));
		dto.setSizeKey(KeyFactory.keyToString(getSizeKey()));
		dto.setColorKey(KeyFactory.keyToString(getColorKey()));
		dto.setBuyPrice(getBuyPrice());
		dto.setSellPrice(getSellPrice());
		dto.setImageKey(KeyFactory.keyToString(getImageKey()));
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
	
	public Long getProductNumber() {
		return productNumber;
	}
	
	public void setProductNumber(Long productNumber) {
		this.productNumber = productNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryKey() {
		return categoryKey;
	}

	public void setCategoryKey(String categoryKey) {
		this.categoryKey = categoryKey;
	}

	public Key getStyleKey() {
		return styleKey;
	}

	public void setStyleId(Key styleId) {
		this.styleKey = styleId;
	}

	public Key getBrandKey() {
		return brandKey;
	}

	public void setBrandKey(Key brandKey) {
		this.brandKey = brandKey;
	}

	public Key getSizeKey() {
		return sizeKey;
	}

	public void setSizeKey(Key sizeKey) {
		this.sizeKey = sizeKey;
	}

	public Key getColorKey() {
		return colorKey;
	}

	public void setColorKey(Key colorKey) {
		this.colorKey = colorKey;
	}

	public Integer getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Integer buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Integer getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Integer sellPrice) {
		this.sellPrice = sellPrice;
	}
	
	public Key getImageKey() {
		return imageKey;
	}
	
	public void setImageKey(Key imageKey) {
		this.imageKey = imageKey;
	}

	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof ArticleType)) return false;
		ArticleType s = (ArticleType) o;
		boolean equal = true;
		equal &= attributeEquals(productNumber, s.getProductNumber());
		equal &= attributeEquals(name, s.getName());
		equal &= attributeEquals(categoryKey, s.getCategoryKey());
		equal &= attributeEquals(styleKey, s.getStyleKey());
		equal &= attributeEquals(brandKey, s.getBrandKey());
		equal &= attributeEquals(sizeKey, s.getSizeKey());
		equal &= attributeEquals(colorKey, s.getColorKey());
		equal &= attributeEquals(buyPrice, s.getBuyPrice());
		equal &= attributeEquals(sellPrice, s.getSellPrice());
		equal &= attributeEquals(imageKey, s.getImageKey());
		return equal;
	}
	
	private boolean attributeEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null) {
			return false;
		}
		return o1.equals(o2);
	}

}
