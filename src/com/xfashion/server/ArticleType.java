package com.xfashion.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.xfashion.shared.ArticleTypeDTO;

@PersistenceCapable
public class ArticleType implements IsSerializable {
	
	public static final String ID_COUNTER_NAME = "ArticleType";
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long productNumber;

	@Persistent
	private String name;
	
	@Persistent
	private Long categoryId;
	
	@Persistent
	private Long styleId;
	
	@Persistent
	private Long brandId;
	
	@Persistent
	private Long sizeId;
	
	@Persistent
	private Long colorId;
	
	@Persistent
	private Integer buyPrice;
	
	@Persistent
	private Integer sellPrice;
	
	@Persistent
	private Long imageId;

	public ArticleType() {
		
	}
	
	public ArticleType(ArticleTypeDTO dto) {
		updateFromDTO(dto);
	}
	
	public void updateFromDTO(ArticleTypeDTO dto) {
		setName(dto.getName());
		setCategoryId(dto.getCategoryId());
		setStyleId(dto.getStyleId());
		setBrandId(dto.getBrandId());
		setSizeId(dto.getSizeId());
		setColorId(dto.getColorId());
		setBuyPrice(dto.getBuyPrice());
		setSellPrice(dto.getSellPrice());
		setImageId(dto.getImageId());
	}
	
	public ArticleTypeDTO createDTO() {
		ArticleTypeDTO dto = new ArticleTypeDTO();
		dto.setProductNumber(getProductNumber());
		dto.setName(getName());
		dto.setCategoryId(getCategoryId());
		dto.setStyleId(getStyleId());
		dto.setBrandId(getBrandId());
		dto.setSizeId(getSizeId());
		dto.setColorId(getColorId());
		dto.setBuyPrice(getBuyPrice());
		dto.setSellPrice(getSellPrice());
		dto.setImageId(getImageId());
		return dto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Long productNumber) {
		this.productNumber = productNumber;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getStyleId() {
		return styleId;
	}

	public void setStyleId(Long styleId) {
		this.styleId = styleId;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getSizeId() {
		return sizeId;
	}

	public void setSizeId(Long sizeId) {
		this.sizeId = sizeId;
	}

	public Long getColorId() {
		return colorId;
	}

	public void setColorId(Long colorId) {
		this.colorId = colorId;
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
	
	public Long getImageId() {
		return imageId;
	}
	
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof ArticleType)) return false;
		ArticleType s = (ArticleType) o;
		boolean equal = true;
		equal &= attributeEquals(productNumber, s.getProductNumber());
		equal &= attributeEquals(name, s.getName());
		equal &= attributeEquals(categoryId, s.getCategoryId());
		equal &= attributeEquals(styleId, s.getStyleId());
		equal &= attributeEquals(brandId, s.getBrandId());
		equal &= attributeEquals(sizeId, s.getSizeId());
		equal &= attributeEquals(colorId, s.getColorId());
		equal &= attributeEquals(buyPrice, s.getBuyPrice());
		equal &= attributeEquals(sellPrice, s.getSellPrice());
		equal &= attributeEquals(imageId, s.getImageId());
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
