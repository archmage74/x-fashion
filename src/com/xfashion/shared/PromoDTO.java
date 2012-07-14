package com.xfashion.shared;


public class PromoDTO extends DTO {

	/** ean-code without checksum digit */
	protected Long ean;

	/** price in cents */
	protected Integer price;

	/** reduction of price in percent */
	protected Integer percent;

	/** if this promo is activated to override article prices */
	protected boolean activated;

	public PromoDTO() {

	}
	
	public PromoDTO(Integer price, Integer percent, Long ean) {
		this(price, percent, ean, true);
	}

	public PromoDTO(Integer price, Integer percent, Long ean, boolean activated) {
		this.price = price;
		this.percent = percent;
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

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

}
