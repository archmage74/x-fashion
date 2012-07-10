package com.xfashion.shared;


public class PromoDTO extends DTO {

	/** ean-code without checksum digit */
	protected Long ean;

	/** price in cents */
	protected Integer price;

	/** if this promo is activated to override article prices */
	protected boolean activated;

	public PromoDTO() {

	}
	
	public PromoDTO(Integer price, Long ean) {
		this(price, ean, true);
	}

	public PromoDTO(Integer price, Long ean, boolean activated) {
		this.price = price;
		this.ean = ean;
		this.activated = activated;
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

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

}
